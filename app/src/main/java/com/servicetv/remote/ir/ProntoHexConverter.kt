package com.servicetv.remote.ir

import kotlin.math.roundToInt

/**
 * Utility for parsing and converting Philips Pronto Hex format into
 * frequency (Hz) and microsecond pulse/space timing arrays suitable
 * for Android's ConsumerIrManager.transmit(carrierFrequency, pattern).
 */
object ProntoHexConverter {

    /**
     * Converts a Philips Pronto Hex string to frequency in Hz and timing pattern in microseconds.
     *
     * Pronto Hex Format:
     * 0000: Learned Code
     * Word 2: Frequency code (Carrier freq = 1000000 / (Code * 0.241246))
     * Word 3: Length of burst pair sequence 1 (once)
     * Word 4: Length of burst pair sequence 2 (repeated)
     * Following words: Alternating pulse/space cycle counts
     */
    fun hexToPattern(prontoHex: String): Pair<Int, IntArray>? {
        try {
            val tokens = prontoHex.trim().split("\\s+".toRegex())
            if (tokens.size < 4) return null

            val codeType = tokens[0].toInt(16)
            // Code type 0000 is standard raw learned format
            if (codeType != 0) {
                // Some codes might omit the 0000 preamble or use direct raw
                return null
            }

            val freqCode = tokens[1].toInt(16)
            if (freqCode == 0) return null
            val carrierFrequency = (1000000.0 / (freqCode * 0.241246)).roundToInt()

            val burst1Length = tokens[2].toInt(16)
            val burst2Length = tokens[3].toInt(16)

            val totalBurstPairs = burst1Length + burst2Length
            val availablePairs = (tokens.size - 4) / 2
            val pairsToRead = minOf(totalBurstPairs, availablePairs)

            if (pairsToRead <= 0) return null

            // 1 cycle duration in microseconds
            val cycleTimeMicroseconds = 1000000.0 / carrierFrequency
            val pattern = IntArray(pairsToRead * 2)

            var tokenIndex = 4
            var patternIndex = 0

            for (i in 0 until pairsToRead) {
                if (tokenIndex + 1 >= tokens.size) break
                val pulseCycles = tokens[tokenIndex++].toInt(16)
                val spaceCycles = tokens[tokenIndex++].toInt(16)

                pattern[patternIndex++] = (pulseCycles * cycleTimeMicroseconds).roundToInt()
                pattern[patternIndex++] = (spaceCycles * cycleTimeMicroseconds).roundToInt()
            }

            return Pair(carrierFrequency, pattern)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
