package com.servicetv.remote.ir

/**
 * NEC Infrared Transmission Protocol Encoder.
 * Standard used by: LG, Samsung (NEC variant), China Universal TV Boards, TCL, etc.
 *
 * Carrier Frequency: 38,000 Hz
 * Leader: 9,000 µs Mark + 4,500 µs Space
 * Logical 0: 560 µs Mark + 560 µs Space
 * Logical 1: 560 µs Mark + 1,690 µs Space
 * Stop Bit: 560 µs Mark
 */
object NecEncoder {
    const val DEFAULT_FREQUENCY = 38000

    private const val LEADER_PULSE = 9000
    private const val LEADER_SPACE = 4500
    private const val BIT_PULSE = 560
    private const val BIT_0_SPACE = 560
    private const val BIT_1_SPACE = 1690
    private const val STOP_PULSE = 560

    /**
     * Builds standard 32-bit NEC timing pattern.
     * @param address 8-bit or 16-bit address
     * @param command 8-bit command
     * @param isExtendedAddress if true, sends 16-bit address without bitwise inversion
     */
    fun encode(address: Int, command: Int, isExtendedAddress: Boolean = false): IntArray {
        val bits = IntArray(32)

        if (isExtendedAddress) {
            // Extended NEC: 16-bit address (LSB first)
            for (i in 0 until 16) {
                bits[i] = (address shr i) and 1
            }
        } else {
            // Standard NEC: 8-bit address + 8-bit inverted address
            val addr8 = address and 0xFF
            val addrInv = (addr8.inv()) and 0xFF
            for (i in 0 until 8) {
                bits[i] = (addr8 shr i) and 1
                bits[i + 8] = (addrInv shr i) and 1
            }
        }

        // 8-bit command + 8-bit inverted command
        val cmd8 = command and 0xFF
        val cmdInv = (cmd8.inv()) and 0xFF
        for (i in 0 until 8) {
            bits[16 + i] = (cmd8 shr i) and 1
            bits[24 + i] = (cmdInv shr i) and 1
        }

        // Pattern length: Leader (2) + 32 bits (32 * 2) + Stop (1) = 67 timings
        val pattern = IntArray(2 + (32 * 2) + 1)
        var idx = 0

        // Leader
        pattern[idx++] = LEADER_PULSE
        pattern[idx++] = LEADER_SPACE

        // 32 Bits
        for (b in bits) {
            pattern[idx++] = BIT_PULSE
            pattern[idx++] = if (b == 1) BIT_1_SPACE else BIT_0_SPACE
        }

        // Stop bit
        pattern[idx] = STOP_PULSE

        return pattern
    }

    /**
     * Encodes a 32-bit hex word directly (e.g. 0x20DFDF20 for LG IN-START)
     * Transmits LSB first for each byte or overall.
     */
    fun encodeHex32(hex32: Long): IntArray {
        val pattern = IntArray(2 + (32 * 2) + 1)
        var idx = 0

        pattern[idx++] = LEADER_PULSE
        pattern[idx++] = LEADER_SPACE

        // Transmit 32 bits (MSB or LSB depending on convention)
        for (i in 31 downTo 0) {
            val bit = ((hex32 shr i) and 1L).toInt()
            pattern[idx++] = BIT_PULSE
            pattern[idx++] = if (bit == 1) BIT_1_SPACE else BIT_0_SPACE
        }

        pattern[idx] = STOP_PULSE
        return pattern
    }
}
