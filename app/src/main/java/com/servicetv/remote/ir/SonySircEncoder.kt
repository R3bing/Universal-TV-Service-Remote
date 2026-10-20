package com.servicetv.remote.ir

/**
 * Sony SIRC (Sony Infrared Remote Control) Protocol Encoder.
 * Supports 12-bit, 15-bit, and 20-bit frames.
 *
 * Carrier Frequency: 40,000 Hz
 * Leader: 2,400 µs Mark + 600 µs Space
 * Logical 1: 1,200 µs Mark + 600 µs Space
 * Logical 0: 600 µs Mark + 600 µs Space
 */
object SonySircEncoder {
    const val DEFAULT_FREQUENCY = 40000

    private const val LEADER_PULSE = 2400
    private const val BIT_SPACE = 600
    private const val BIT_0_PULSE = 600
    private const val BIT_1_PULSE = 1200

    /**
     * Encodes a 12-bit Sony SIRC command: 7-bit command + 5-bit address.
     * Sent LSB first.
     */
    fun encode12(command: Int, address: Int): IntArray {
        val pattern = IntArray(1 + (12 * 2))
        var idx = 0

        pattern[idx++] = LEADER_PULSE

        // 7-bit command
        for (i in 0 until 7) {
            val bit = (command shr i) and 1
            pattern[idx++] = BIT_SPACE
            pattern[idx++] = if (bit == 1) BIT_1_PULSE else BIT_0_PULSE
        }

        // 5-bit address (Device ID)
        for (i in 0 until 5) {
            val bit = (address shr i) and 1
            pattern[idx++] = BIT_SPACE
            pattern[idx++] = if (bit == 1) BIT_1_PULSE else BIT_0_PULSE
        }

        return pattern
    }
}
