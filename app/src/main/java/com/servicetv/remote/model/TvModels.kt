package com.servicetv.remote.model

/**
 * Representation of a key/button on the remote control.
 */
data class RemoteButton(
    val id: String,
    val label: String,
    val subLabel: String? = null,
    val category: ButtonCategory = ButtonCategory.STANDARD,
    val prontoHex: String? = null,
    val necAddress: Int? = null,
    val necCommand: Int? = null,
    val macroSteps: List<String>? = null, // Button IDs to execute sequentially
    val isDanger: Boolean = false // E.g., Factory Reset / IN-STOP
)

enum class ButtonCategory {
    POWER,
    NAVIGATION,
    VOLUME_CHANNEL,
    NUMERIC,
    STANDARD,
    SERVICE_DISCRETE,
    SERVICE_MACRO,
    TECHNICIAN_TOOL
}

/**
 * Representation of an automated Service Mode macro sequence.
 */
data class ServiceMacro(
    val name: String,
    val description: String,
    val steps: List<String>, // Sequence of button presses
    val stepDelayMs: Long = 350L, // Delay between consecutive key bursts
    val targetBrandOrChassis: String,
    val promptNotes: String? = null
)

/**
 * Comprehensive profile for a TV Brand or Universal Board family.
 */
data class TvBrand(
    val id: String,
    val name: String,
    val category: String, // "Global Brand", "China Motherboard / Assembled", etc.
    val defaultPins: List<String> = emptyList(), // e.g. "0413", "1147", "2580"
    val discreteServiceButtons: List<RemoteButton> = emptyList(),
    val serviceMacros: List<ServiceMacro> = emptyList(),
    val standardButtons: Map<String, RemoteButton> = emptyMap(),
    val serviceInstructions: String = ""
)
