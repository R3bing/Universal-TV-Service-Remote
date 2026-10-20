package com.servicetv.remote.ir

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

/**
 * High-level manager for physical IR blaster operations.
 * Coordinates hardware ConsumerIrManager, Pronto Hex parsing,
 * Protocol encoders, and fallback simulation for testing.
 */
class IrBlasterManager(private val context: Context) {

    private val tag = "IrBlasterManager"

    private val irService: ConsumerIrManager? by lazy {
        try {
            context.getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager
        } catch (e: Exception) {
            Log.e(tag, "Failed to get ConsumerIrManager", e)
            null
        }
    }

    private val vibrator: Vibrator? by lazy {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Checks if the device has a physical IR blaster hardware emitter.
     */
    fun hasIrEmitter(): Boolean {
        return try {
            irService?.hasIrEmitter() == true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Returns the carrier frequency ranges supported by the hardware emitter.
     */
    fun getSupportedFrequencyRanges(): List<String> {
        return try {
            irService?.carrierFrequencies?.map { range ->
                "${range.minFrequency} Hz - ${range.maxFrequency} Hz"
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Vibrates device briefly to provide tactile physical remote feel.
     */
    fun triggerHapticFeedback(durationMs: Long = 35) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (e: Exception) {
            // Ignore vibration errors
        }
    }

    /**
     * Transmits raw microsecond pulse/space pattern.
     */
    fun transmit(carrierFrequency: Int, pattern: IntArray): Boolean {
        triggerHapticFeedback()

        if (irService == null || !hasIrEmitter()) {
            Log.d(tag, "[EMULATION] Transmitted $carrierFrequency Hz, pattern size: ${pattern.size}")
            return false // Hardware not present, but logged
        }

        return try {
            irService?.transmit(carrierFrequency, pattern)
            Log.d(tag, "Successfully transmitted IR signal at $carrierFrequency Hz (${pattern.size} pulses)")
            true
        } catch (e: Exception) {
            Log.e(tag, "IR transmission failed", e)
            false
        }
    }

    /**
     * Transmits a Philips Pronto Hex string.
     */
    fun transmitProntoHex(prontoHex: String): Boolean {
        val parsed = ProntoHexConverter.hexToPattern(prontoHex) ?: return false
        val (carrierFrequency, pattern) = parsed
        return transmit(carrierFrequency, pattern)
    }

    /**
     * Transmits NEC command.
     */
    fun transmitNec(address: Int, command: Int, isExtended: Boolean = false): Boolean {
        val pattern = NecEncoder.encode(address, command, isExtended)
        return transmit(NecEncoder.DEFAULT_FREQUENCY, pattern)
    }

    /**
     * Transmits Sony SIRC command.
     */
    fun transmitSony(command: Int, address: Int): Boolean {
        val pattern = SonySircEncoder.encode12(command, address)
        return transmit(SonySircEncoder.DEFAULT_FREQUENCY, pattern)
    }
}
