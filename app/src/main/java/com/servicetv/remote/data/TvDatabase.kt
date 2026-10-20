package com.servicetv.remote.data

import com.servicetv.remote.model.ButtonCategory
import com.servicetv.remote.model.RemoteButton
import com.servicetv.remote.model.ServiceMacro
import com.servicetv.remote.model.TvBrand

/**
 * Master Repository of TV Brands, IR Codes, Discrete Service Keys,
 * and Technician Service Mode Macros.
 */
object TvDatabase {

    // Common standard buttons helper
    private fun createStandardButtons(
        powerNec: Int,
        menuNec: Int,
        inputNec: Int,
        volUpNec: Int,
        volDownNec: Int,
        chUpNec: Int,
        chDownNec: Int,
        muteNec: Int,
        okNec: Int,
        upNec: Int,
        downNec: Int,
        leftNec: Int,
        rightNec: Int,
        backNec: Int,
        homeNec: Int,
        digits: List<Int> // 0 to 9
    ): Map<String, RemoteButton> {
        val map = mutableMapOf<String, RemoteButton>()
        map["POWER"] = RemoteButton("POWER", "POWER", category = ButtonCategory.POWER, necCommand = powerNec)
        map["MENU"] = RemoteButton("MENU", "MENU", category = ButtonCategory.STANDARD, necCommand = menuNec)
        map["INPUT"] = RemoteButton("INPUT", "SOURCE", category = ButtonCategory.STANDARD, necCommand = inputNec)
        map["VOL_UP"] = RemoteButton("VOL_UP", "VOL +", category = ButtonCategory.VOLUME_CHANNEL, necCommand = volUpNec)
        map["VOL_DOWN"] = RemoteButton("VOL_DOWN", "VOL -", category = ButtonCategory.VOLUME_CHANNEL, necCommand = volDownNec)
        map["CH_UP"] = RemoteButton("CH_UP", "CH +", category = ButtonCategory.VOLUME_CHANNEL, necCommand = chUpNec)
        map["CH_DOWN"] = RemoteButton("CH_DOWN", "CH -", category = ButtonCategory.VOLUME_CHANNEL, necCommand = chDownNec)
        map["MUTE"] = RemoteButton("MUTE", "MUTE", category = ButtonCategory.STANDARD, necCommand = muteNec)
        map["OK"] = RemoteButton("OK", "OK", category = ButtonCategory.NAVIGATION, necCommand = okNec)
        map["UP"] = RemoteButton("UP", "▲", category = ButtonCategory.NAVIGATION, necCommand = upNec)
        map["DOWN"] = RemoteButton("DOWN", "▼", category = ButtonCategory.NAVIGATION, necCommand = downNec)
        map["LEFT"] = RemoteButton("LEFT", "◄", category = ButtonCategory.NAVIGATION, necCommand = leftNec)
        map["RIGHT"] = RemoteButton("RIGHT", "►", category = ButtonCategory.NAVIGATION, necCommand = rightNec)
        map["BACK"] = RemoteButton("BACK", "RETURN", category = ButtonCategory.NAVIGATION, necCommand = backNec)
        map["HOME"] = RemoteButton("HOME", "HOME", category = ButtonCategory.STANDARD, necCommand = homeNec)

        digits.forEachIndexed { num, cmd ->
            map["DIGIT_$num"] = RemoteButton("DIGIT_$num", "$num", category = ButtonCategory.NUMERIC, necCommand = cmd)
        }

        return map
    }

    val brands: List<TvBrand> = listOf(
        // ==========================================
        // 1. LG ELECTRONICS (WebOS / OLED / LED)
        // ==========================================
        TvBrand(
            id = "lg",
            name = "LG Electronics (WebOS / OLED / LCD)",
            category = "Global Brand",
            defaultPins = listOf("0413", "0000", "7777", "8741", "8743"),
            serviceInstructions = "Point phone at TV. Press IN-START or EZ-ADJUST button. When PIN prompt appears, enter 0413 (or 0000). Use D-pad to navigate. Press BACK to exit.",
            discreteServiceButtons = listOf(
                RemoteButton(
                    id = "LG_IN_START",
                    label = "IN-START",
                    subLabel = "Service Menu",
                    category = ButtonCategory.SERVICE_DISCRETE,
                    prontoHex = "0000 006d 0022 0002 0155 00aa 0015 0015 0015 0015 0015 0040 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0040 0015 0015 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0015 0015 0040 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0015 0015 0040 0015 05f0"
                ),
                RemoteButton(
                    id = "LG_EZ_ADJUST",
                    label = "EZ-ADJUST",
                    subLabel = "Tool Option / Panel",
                    category = ButtonCategory.SERVICE_DISCRETE,
                    prontoHex = "0000 006d 0022 0002 0155 00aa 0015 0015 0015 0015 0015 0040 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0040 0015 0015 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 05f0"
                ),
                RemoteButton(
                    id = "LG_IN_STOP",
                    label = "IN-STOP",
                    subLabel = "Deep Factory Reset",
                    category = ButtonCategory.SERVICE_DISCRETE,
                    isDanger = true,
                    prontoHex = "0000 006d 0022 0002 0155 00aa 0015 0015 0015 0015 0015 0040 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0040 0015 0015 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0015 0015 0040 0015 0015 0015 0040 0015 0015 0015 0040 0015 0015 0015 0040 0015 0040 0015 0015 0015 0040 0015 0015 0015 0040 0015 0015 0015 0040 0015 0015 0015 05f0"
                ),
                RemoteButton(
                    id = "LG_POWER_ONLY",
                    label = "POWER-ONLY",
                    subLabel = "Aging White Mode",
                    category = ButtonCategory.SERVICE_DISCRETE,
                    prontoHex = "0000 006d 0022 0002 0155 00aa 0015 0015 0015 0015 0015 0040 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0040 0015 0015 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0040 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 05f0"
                )
            ),
            standardButtons = createStandardButtons(
                powerNec = 0x10, menuNec = 0x43, inputNec = 0x0B,
                volUpNec = 0x02, volDownNec = 0x03, chUpNec = 0x00, chDownNec = 0x01,
                muteNec = 0x09, okNec = 0x44, upNec = 0x40, downNec = 0x41,
                leftNec = 0x07, rightNec = 0x06, backNec = 0x28, homeNec = 0x7C,
                digits = listOf(0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A)
            )
        ),

        // ==========================================
        // 2. SAMSUNG ELECTRONICS (Tizen / Crystal UHD / QLED)
        // ==========================================
        TvBrand(
            id = "samsung",
            name = "Samsung Electronics (Tizen / Smart TV)",
            category = "Global Brand",
            defaultPins = listOf("0000", "1234"),
            serviceInstructions = "Method 1: When TV is in Standby (OFF), tap 'Macro: Mute+1+8+2+Power'. TV will power on directly into Service Menu.\nMethod 2: When TV is ON, tap FACTORY then 3SPEED to unlock hidden advanced options.",
            discreteServiceButtons = listOf(
                RemoteButton(
                    id = "SAMSUNG_FACTORY",
                    label = "FACTORY",
                    subLabel = "Discrete Service Key",
                    category = ButtonCategory.SERVICE_DISCRETE,
                    prontoHex = "0000 006D 0000 0022 00AC 00AB 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0016 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0689"
                ),
                RemoteButton(
                    id = "SAMSUNG_3SPEED",
                    label = "3SPEED",
                    subLabel = "Unlock Advanced LocalSet",
                    category = ButtonCategory.SERVICE_DISCRETE,
                    prontoHex = "0000 006D 0000 0022 00AC 00AB 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0041 0015 0041 0015 0016 0015 0016 0015 0041 0015 0041 0015 0016 0015 0016 0015 0016 0015 0016 0015 0041 0015 0041 0015 0689"
                )
            ),
            serviceMacros = listOf(
                ServiceMacro(
                    name = "Standby Macro: MUTE + 1 + 8 + 2 + POWER",
                    description = "Turn TV OFF (Standby), then press this macro. Boots directly into Service Menu.",
                    steps = listOf("MUTE", "DIGIT_1", "DIGIT_8", "DIGIT_2", "POWER"),
                    stepDelayMs = 300L,
                    targetBrandOrChassis = "Samsung (All Models)"
                ),
                ServiceMacro(
                    name = "Live Macro: INFO + FACTORY",
                    description = "When TV is already ON, triggers instant service overlay.",
                    steps = listOf("INFO", "SAMSUNG_FACTORY"),
                    stepDelayMs = 250L,
                    targetBrandOrChassis = "Samsung Smart"
                ),
                ServiceMacro(
                    name = "Alternative Macro: INFO + MENU + MUTE + POWER",
                    description = "Used on European and Asian market models.",
                    steps = listOf("INFO", "MENU", "MUTE", "POWER"),
                    stepDelayMs = 300L,
                    targetBrandOrChassis = "Samsung Legacy"
                )
            ),
            standardButtons = createStandardButtons(
                powerNec = 0x02, menuNec = 0x1A, inputNec = 0x01,
                volUpNec = 0x07, volDownNec = 0x0B, chUpNec = 0x12, chDownNec = 0x10,
                muteNec = 0x0F, okNec = 0x68, upNec = 0x60, downNec = 0x61,
                leftNec = 0x65, rightNec = 0x62, backNec = 0x58, homeNec = 0x79,
                digits = listOf(0x11, 0x04, 0x05, 0x06, 0x08, 0x09, 0x0A, 0x0C, 0x0D, 0x0E)
            )
        ),

        // ==========================================
        // 3. CHINA UNIVERSAL BOARDS (MStar / Realtek / CVTE / Amlogic)
        // (Powers Vu, Micromax, Shinco, Onida, Lloyd, Sansui, Assembled TVs)
        // ==========================================
        TvBrand(
            id = "china_universal",
            name = "China Universal Motherboards (MStar / RTD / CVTE)",
            category = "Universal Motherboard",
            defaultPins = listOf("1147", "2580", "815", "208", "8202", "0000"),
            serviceInstructions = "Universal boards use standard key sequence combinations while on home/source screen. Tap the appropriate macro below depending on your motherboard model (e.g. TP.MS338 -> Menu 1147, CV338H -> Input 2580).",
            discreteServiceButtons = listOf(
                RemoteButton("FIX_MIRROR", "MIRROR FLIP", "Panel Direction", category = ButtonCategory.TECHNICIAN_TOOL),
                RemoteButton("FIX_LVDS", "LVDS MAP (JEIDA/VESA)", "Color Distortion Fix", category = ButtonCategory.TECHNICIAN_TOOL),
                RemoteButton("FIX_AGING", "EXIT AGING MODE", "Stop RGB Color Cycle", category = ButtonCategory.TECHNICIAN_TOOL)
            ),
            serviceMacros = listOf(
                ServiceMacro(
                    name = "MENU + 1 1 4 7 (Most Popular)",
                    description = "Used for TP.MS338, T.VST59, T.V56, TP.HV320, SKR.03, T.R83. Fixes Panel, LVDS, Mirror.",
                    steps = listOf("MENU", "DIGIT_1", "DIGIT_1", "DIGIT_4", "DIGIT_7"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "TP.MS338 / T.VST59 / V56"
                ),
                ServiceMacro(
                    name = "INPUT / SOURCE + 2 5 8 0",
                    description = "Used for CV182, CV338, CV56BH, CVMV26, TP.VST59.PB819.",
                    steps = listOf("INPUT", "DIGIT_2", "DIGIT_5", "DIGIT_8", "DIGIT_0"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "CVTE / CV338H Boards"
                ),
                ServiceMacro(
                    name = "INPUT + 2 0 8",
                    description = "Used for VS.TP53U71, VS.TP53U61 and Smart Android combo boards.",
                    steps = listOf("INPUT", "DIGIT_2", "DIGIT_0", "DIGIT_8"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "VS.TP53 Series"
                ),
                ServiceMacro(
                    name = "INPUT + 8 1 5 (Smart LED)",
                    description = "Alternative smart combo firmware resolution and user data reset.",
                    steps = listOf("INPUT", "DIGIT_8", "DIGIT_1", "DIGIT_5"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "Smart Board Combo"
                ),
                ServiceMacro(
                    name = "MENU + 8 2 0 2 (Legacy)",
                    description = "For older China LCD/CRT universal boards.",
                    steps = listOf("MENU", "DIGIT_8", "DIGIT_2", "DIGIT_0", "DIGIT_2"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "China Legacy Boards"
                ),
                ServiceMacro(
                    name = "INPUT + 3 1 3 8 / 6 4 8 3",
                    description = "Used for Skyworth, Haier, Changhong motherboards.",
                    steps = listOf("INPUT", "DIGIT_3", "DIGIT_1", "DIGIT_3", "DIGIT_8"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "Skyworth / Haier"
                )
            ),
            standardButtons = createStandardButtons(
                powerNec = 0x14, menuNec = 0x0F, inputNec = 0x01,
                volUpNec = 0x12, volDownNec = 0x10, chUpNec = 0x1A, chDownNec = 0x1B,
                muteNec = 0x15, okNec = 0x13, upNec = 0x05, downNec = 0x0D,
                leftNec = 0x06, rightNec = 0x07, backNec = 0x17, homeNec = 0x1F,
                digits = listOf(0x00, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0E, 0x11, 0x18, 0x19)
            )
        ),

        // ==========================================
        // 4. SONY BRAVIA (Google TV / Android / Bravia XR)
        // ==========================================
        TvBrand(
            id = "sony",
            name = "Sony Bravia (Google TV / Android / Classic)",
            category = "Global Brand",
            defaultPins = listOf("0000", "9999"),
            serviceInstructions = "Turn TV OFF into Standby. Tap 'Standby Macro: Display + 5 + VolUp + Power'. The TV will switch on and display the Service / Self-Diagnostic panel.",
            serviceMacros = listOf(
                ServiceMacro(
                    name = "Standby Macro: DISPLAY + 5 + VOL_UP + POWER",
                    description = "Standard Sony Bravia Service Mode entry from standby.",
                    steps = listOf("DISPLAY", "DIGIT_5", "VOL_UP", "POWER"),
                    stepDelayMs = 350L,
                    targetBrandOrChassis = "Sony Bravia (All Generations)"
                ),
                ServiceMacro(
                    name = "Diagnostic Macro: DISPLAY + 5 + VOL_DOWN + POWER",
                    description = "Opens Error History & Self-Diagnostic Screen (Blink Code Counter).",
                    steps = listOf("DISPLAY", "DIGIT_5", "VOL_DOWN", "POWER"),
                    stepDelayMs = 350L,
                    targetBrandOrChassis = "Sony Diagnostic Counter"
                ),
                ServiceMacro(
                    name = "Modern Smart Macro: HOME + OK + VOL_UP + POWER",
                    description = "For newer remote models without direct numeric buttons.",
                    steps = listOf("HOME", "OK", "VOL_UP", "POWER"),
                    stepDelayMs = 350L,
                    targetBrandOrChassis = "Sony Smart Remote"
                )
            ),
            standardButtons = createStandardButtons(
                powerNec = 0x15, menuNec = 0x60, inputNec = 0x25,
                volUpNec = 0x12, volDownNec = 0x13, chUpNec = 0x10, chDownNec = 0x11,
                muteNec = 0x14, okNec = 0x65, upNec = 0x74, downNec = 0x75,
                leftNec = 0x33, rightNec = 0x34, backNec = 0x23, homeNec = 0x60,
                digits = listOf(0x09, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08)
            ).toMutableMap().apply {
                this["DISPLAY"] = RemoteButton("DISPLAY", "INFO/DISP", category = ButtonCategory.STANDARD, necCommand = 0x3A)
            }
        ),

        // ==========================================
        // 5. TCL / THOMSON / iFFALCON
        // ==========================================
        TvBrand(
            id = "tcl",
            name = "TCL / Thomson / iFFALCON",
            category = "Global Brand",
            defaultPins = listOf("1234", "0428"),
            serviceInstructions = "Navigate to TV Settings -> Picture -> Advanced Settings -> Highlight 'Contrast'. Then tap Macro: Contrast + 9 + 7 + 3 + 5.",
            serviceMacros = listOf(
                ServiceMacro(
                    name = "Technician Code: 9 7 3 5",
                    description = "Highlight Contrast in Picture settings and tap to enter Factory Menu.",
                    steps = listOf("DIGIT_9", "DIGIT_7", "DIGIT_3", "DIGIT_5"),
                    stepDelayMs = 300L,
                    targetBrandOrChassis = "TCL / Thomson Factory Mode"
                ),
                ServiceMacro(
                    name = "Design Code: 1 9 5 0",
                    description = "Secondary Service Menu code for newer TCL Google TV chassis.",
                    steps = listOf("DIGIT_1", "DIGIT_9", "DIGIT_5", "DIGIT_0"),
                    stepDelayMs = 300L,
                    targetBrandOrChassis = "TCL Design Mode"
                ),
                ServiceMacro(
                    name = "Service Code: 6 4 2 5",
                    description = "Alternative code for older TCL smart chassis.",
                    steps = listOf("DIGIT_6", "DIGIT_4", "DIGIT_2", "DIGIT_5"),
                    stepDelayMs = 300L,
                    targetBrandOrChassis = "TCL Alternative"
                )
            ),
            standardButtons = createStandardButtons(
                powerNec = 0x12, menuNec = 0x01, inputNec = 0x14,
                volUpNec = 0x1A, volDownNec = 0x1E, chUpNec = 0x1B, chDownNec = 0x1F,
                muteNec = 0x17, okNec = 0x07, upNec = 0x02, downNec = 0x03,
                leftNec = 0x04, rightNec = 0x05, backNec = 0x06, homeNec = 0x24,
                digits = listOf(0x00, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10)
            )
        ),

        // ==========================================
        // 6. PANASONIC VIERA
        // ==========================================
        TvBrand(
            id = "panasonic",
            name = "Panasonic (Viera / Smart LED)",
            category = "Global Brand",
            defaultPins = listOf("0000"),
            serviceInstructions = "Hold Volume Down on physical TV button panel and tap the Service Key Macro.",
            serviceMacros = listOf(
                ServiceMacro(
                    name = "Service Menu Macro (Info x 3)",
                    description = "Hold VOL- on the TV cabinet, then trigger this button sequence.",
                    steps = listOf("INFO", "INFO", "INFO"),
                    stepDelayMs = 400L,
                    targetBrandOrChassis = "Panasonic Viera"
                )
            ),
            standardButtons = createStandardButtons(
                powerNec = 0x3D, menuNec = 0x32, inputNec = 0x02,
                volUpNec = 0x20, volDownNec = 0x21, chUpNec = 0x2C, chDownNec = 0x2D,
                muteNec = 0x30, okNec = 0x73, upNec = 0x68, downNec = 0x69,
                leftNec = 0x6A, rightNec = 0x6B, backNec = 0x2B, homeNec = 0x32,
                digits = listOf(0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
            ).toMutableMap().apply {
                this["INFO"] = RemoteButton("INFO", "INFO", category = ButtonCategory.STANDARD, necCommand = 0x35)
            }
        ),

        // ==========================================
        // 7. PHILIPS TV
        // ==========================================
        TvBrand(
            id = "philips",
            name = "Philips (Ambilight / Android TV)",
            category = "Global Brand",
            defaultPins = listOf("8888", "1234"),
            serviceInstructions = "While watching TV or on Home screen, trigger the SAM (Service Alignment Mode) macro.",
            serviceMacros = listOf(
                ServiceMacro(
                    name = "SAM Macro: 0 6 2 5 9 6 + INFO / OK",
                    description = "Standard Philips Service Alignment Mode trigger code.",
                    steps = listOf("DIGIT_0", "DIGIT_6", "DIGIT_2", "DIGIT_5", "DIGIT_9", "DIGIT_6", "INFO"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "Philips SAM Mode"
                ),
                ServiceMacro(
                    name = "CSM Macro: 1 2 3 6 5 4",
                    description = "Customer Service Mode (Read-only status & hours).",
                    steps = listOf("DIGIT_1", "DIGIT_2", "DIGIT_3", "DIGIT_6", "DIGIT_5", "DIGIT_4"),
                    stepDelayMs = 280L,
                    targetBrandOrChassis = "Philips CSM Status"
                )
            ),
            standardButtons = createStandardButtons(
                powerNec = 0x0C, menuNec = 0x0A, inputNec = 0x38,
                volUpNec = 0x10, volDownNec = 0x11, chUpNec = 0x20, chDownNec = 0x21,
                muteNec = 0x0D, okNec = 0x5C, upNec = 0x58, downNec = 0x59,
                leftNec = 0x5A, rightNec = 0x5B, backNec = 0x0B, homeNec = 0x0A,
                digits = listOf(0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
            ).toMutableMap().apply {
                this["INFO"] = RemoteButton("INFO", "INFO", category = ButtonCategory.STANDARD, necCommand = 0x0F)
            }
        ),

        // ==========================================
        // 8. XIAOMI / MI / REDMI TV
        // ==========================================
        TvBrand(
            id = "xiaomi",
            name = "Xiaomi / Mi / Redmi TV (PatchWall / Android)",
            category = "Global Brand",
            defaultPins = listOf("0000"),
            serviceInstructions = "Mi TVs utilize Bluetooth primarily, but accept standard NEC IR codes on IR-equipped units. For recovery menu: Turn off power at socket, hold Back + OK while powering on.",
            standardButtons = createStandardButtons(
                powerNec = 0x0C, menuNec = 0x18, inputNec = 0x1A,
                volUpNec = 0x10, volDownNec = 0x11, chUpNec = 0x20, chDownNec = 0x21,
                muteNec = 0x15, okNec = 0x0D, upNec = 0x04, downNec = 0x05,
                leftNec = 0x06, rightNec = 0x07, backNec = 0x14, homeNec = 0x19,
                digits = listOf(0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09)
            )
        )
    )
}
