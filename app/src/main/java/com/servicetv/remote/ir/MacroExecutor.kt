package com.servicetv.remote.ir

import com.servicetv.remote.model.RemoteButton
import com.servicetv.remote.model.ServiceMacro
import com.servicetv.remote.model.TvBrand
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class MacroStatus {
    object Idle : MacroStatus()
    data class Transmitting(val currentStep: Int, val totalSteps: Int, val keyName: String) : MacroStatus()
    data class Completed(val macroName: String) : MacroStatus()
    data class Error(val message: String) : MacroStatus()
}

/**
 * Handles executing automated timed sequences of IR commands (Macros).
 */
class MacroExecutor(private val irBlasterManager: IrBlasterManager) {

    private val _status = MutableStateFlow<MacroStatus>(MacroStatus.Idle)
    val status: StateFlow<MacroStatus> = _status.asStateFlow()

    /**
     * Executes a ServiceMacro for a selected TV brand.
     */
    suspend fun executeMacro(macro: ServiceMacro, brand: TvBrand) {
        val total = macro.steps.size
        try {
            for (index in macro.steps.indices) {
                val stepKeyId = macro.steps[index]
                _status.value = MacroStatus.Transmitting(index + 1, total, stepKeyId)

                // Resolve button from brand standard or discrete buttons
                val button = brand.standardButtons[stepKeyId]
                    ?: brand.discreteServiceButtons.firstOrNull { it.id == stepKeyId }

                transmitButton(button, brand)

                // Precise delay between key bursts
                delay(macro.stepDelayMs)
            }

            _status.value = MacroStatus.Completed(macro.name)
            delay(1200)
            _status.value = MacroStatus.Idle
        } catch (e: Exception) {
            _status.value = MacroStatus.Error(e.message ?: "Macro execution failed")
            delay(2000)
            _status.value = MacroStatus.Idle
        }
    }

    /**
     * Transmits a single RemoteButton via Pronto Hex or NEC.
     */
    fun transmitButton(button: RemoteButton?, brand: TvBrand): Boolean {
        if (button == null) return false

        // 1. If Pronto Hex is provided (highest priority, exact match)
        if (!button.prontoHex.isNullOrBlank()) {
            return irBlasterManager.transmitProntoHex(button.prontoHex)
        }

        // 2. If NEC command is specified
        if (button.necCommand != null) {
            val address = button.necAddress ?: 0x00
            return irBlasterManager.transmitNec(address, button.necCommand)
        }

        return false
    }
}
