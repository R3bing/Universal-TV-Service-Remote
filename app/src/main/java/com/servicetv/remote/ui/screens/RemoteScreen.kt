package com.servicetv.remote.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.servicetv.remote.data.TvDatabase
import com.servicetv.remote.ir.IrBlasterManager
import com.servicetv.remote.ir.MacroExecutor
import com.servicetv.remote.model.RemoteButton
import com.servicetv.remote.model.TvBrand
import com.servicetv.remote.ui.components.CustomHexDialog
import com.servicetv.remote.ui.components.DPadController
import com.servicetv.remote.ui.components.MotherboardLookupDialog
import com.servicetv.remote.ui.components.ServiceMenuDialog
import com.servicetv.remote.ui.components.TactileButton
import com.servicetv.remote.ui.theme.AccentAmber
import com.servicetv.remote.ui.theme.AccentCyan
import com.servicetv.remote.ui.theme.ButtonNeutral
import com.servicetv.remote.ui.theme.ButtonNeutralBorder
import com.servicetv.remote.ui.theme.DangerRed
import com.servicetv.remote.ui.theme.DarkBackground
import com.servicetv.remote.ui.theme.SuccessGreen
import com.servicetv.remote.ui.theme.SurfaceCard
import com.servicetv.remote.ui.theme.SurfaceCardElevated
import com.servicetv.remote.ui.theme.TextPrimary
import com.servicetv.remote.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun RemoteScreen(
    irBlasterManager: IrBlasterManager,
    macroExecutor: MacroExecutor
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedBrand by remember { mutableStateOf<TvBrand>(TvDatabase.brands.first()) }
    var brandDropdownExpanded by remember { mutableStateOf(false) }

    // Dialog state controllers
    var showServiceHub by remember { mutableStateOf(false) }
    var showBoardLookup by remember { mutableStateOf(false) }
    var showCustomHex by remember { mutableStateOf(false) }
    var showNumberPad by remember { mutableStateOf(true) }

    val hasHardwareIr = remember { irBlasterManager.hasIrEmitter() }

    fun sendKey(keyId: String) {
        val button = selectedBrand.standardButtons[keyId]
        macroExecutor.transmitButton(button, selectedBrand)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ==========================================
            // Top Bar: Hardware Status & Brand Selector
            // ==========================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // IR Hardware Indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(SurfaceCard, RoundedCornerShape(12.dp))
                        .border(1.dp, if (hasHardwareIr) SuccessGreen else AccentAmber, RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (hasHardwareIr) SuccessGreen else AccentAmber)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (hasHardwareIr) "IR READY" else "EMULATION",
                        color = if (hasHardwareIr) SuccessGreen else AccentAmber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Brand Selector Dropdown
                Box {
                    Row(
                        modifier = Modifier
                            .background(SurfaceCardElevated, RoundedCornerShape(12.dp))
                            .border(1.dp, ButtonNeutralBorder, RoundedCornerShape(12.dp))
                            .clickable { brandDropdownExpanded = true }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedBrand.name.take(18) + (if (selectedBrand.name.length > 18) "..." else ""),
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select Brand",
                            tint = AccentCyan
                        )
                    }

                    DropdownMenu(
                        expanded = brandDropdownExpanded,
                        onDismissRequest = { brandDropdownExpanded = false },
                        modifier = Modifier.background(SurfaceCardElevated)
                    ) {
                        TvDatabase.brands.forEach { brand ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(brand.name, color = TextPrimary, fontWeight = FontWeight.Bold)
                                        Text(brand.category, color = TextSecondary, fontSize = 10.sp)
                                    }
                                },
                                onClick = {
                                    selectedBrand = brand
                                    brandDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ==========================================
            // Master Technician Action Bar
            // ==========================================
            // The centerpiece requested by user: Big Service Mode button
            TactileButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                label = "⚡ SERVICE MODE (FACTORY MENU)",
                subLabel = "Discrete IR Keys • 1-Tap Macros • Mirror/LVDS Fixes",
                backgroundColor = AccentAmber.copy(alpha = 0.2f),
                borderColor = AccentAmber,
                textColor = AccentAmber,
                cornerRadius = 16.dp,
                onClick = { showServiceHub = true }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Secondary Quick Tools
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TactileButton(
                    modifier = Modifier.weight(1f).height(44.dp),
                    label = "🔍 BOARD LOOKUP",
                    subLabel = "China Board Codes",
                    backgroundColor = AccentCyan.copy(alpha = 0.12f),
                    borderColor = AccentCyan,
                    textColor = AccentCyan,
                    onClick = { showBoardLookup = true }
                )
                TactileButton(
                    modifier = Modifier.weight(1f).height(44.dp),
                    label = "⚡ HEX INJECTOR",
                    subLabel = "Raw Pronto IR",
                    backgroundColor = ButtonNeutral,
                    borderColor = ButtonNeutralBorder,
                    textColor = TextPrimary,
                    onClick = { showCustomHex = true }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ==========================================
            // Standard TV Remote Physical Controls
            // ==========================================

            // Power, Source, Mute Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Power Button
                TactileButton(
                    modifier = Modifier.size(54.dp),
                    label = "⏻",
                    backgroundColor = DangerRed.copy(alpha = 0.2f),
                    borderColor = DangerRed,
                    textColor = DangerRed,
                    cornerRadius = 27.dp,
                    onClick = { sendKey("POWER") }
                )

                // Input / Source Button
                TactileButton(
                    modifier = Modifier.width(90.dp).height(46.dp),
                    label = "SOURCE",
                    backgroundColor = AccentCyan.copy(alpha = 0.12f),
                    borderColor = AccentCyan,
                    textColor = AccentCyan,
                    onClick = { sendKey("INPUT") }
                )

                // Mute Button
                TactileButton(
                    modifier = Modifier.size(54.dp),
                    label = "MUTE",
                    backgroundColor = ButtonNeutral,
                    borderColor = ButtonNeutralBorder,
                    textColor = TextPrimary,
                    cornerRadius = 27.dp,
                    onClick = { sendKey("MUTE") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Circular D-Pad Controller
            DPadController(
                onUp = { sendKey("UP") },
                onDown = { sendKey("DOWN") },
                onLeft = { sendKey("LEFT") },
                onRight = { sendKey("RIGHT") },
                onOk = { sendKey("OK") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Navigation Row: Back, Home, Menu
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TactileButton(
                    modifier = Modifier.width(76.dp).height(44.dp),
                    label = "BACK",
                    onClick = { sendKey("BACK") }
                )
                TactileButton(
                    modifier = Modifier.width(76.dp).height(44.dp),
                    label = "HOME",
                    onClick = { sendKey("HOME") }
                )
                TactileButton(
                    modifier = Modifier.width(76.dp).height(44.dp),
                    label = "MENU",
                    onClick = { sendKey("MENU") }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Volume & Channel Pill Rockers
            Row(
                modifier = Modifier.fillMaxWidth(0.85f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Volume Column
                Column(
                    modifier = Modifier
                        .background(SurfaceCardElevated, RoundedCornerShape(24.dp))
                        .border(1.dp, ButtonNeutralBorder, RoundedCornerShape(24.dp))
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TactileButton(
                        modifier = Modifier.size(56.dp, 48.dp),
                        label = "+",
                        cornerRadius = 20.dp,
                        onClick = { sendKey("VOL_UP") }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("VOL", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    TactileButton(
                        modifier = Modifier.size(56.dp, 48.dp),
                        label = "-",
                        cornerRadius = 20.dp,
                        onClick = { sendKey("VOL_DOWN") }
                    )
                }

                // Channel Column
                Column(
                    modifier = Modifier
                        .background(SurfaceCardElevated, RoundedCornerShape(24.dp))
                        .border(1.dp, ButtonNeutralBorder, RoundedCornerShape(24.dp))
                        .padding(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TactileButton(
                        modifier = Modifier.size(56.dp, 48.dp),
                        label = "▲",
                        cornerRadius = 20.dp,
                        onClick = { sendKey("CH_UP") }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("CH", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    TactileButton(
                        modifier = Modifier.size(56.dp, 48.dp),
                        label = "▼",
                        cornerRadius = 20.dp,
                        onClick = { sendKey("CH_DOWN") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ==========================================
            // Collapsible Numeric Keypad (0-9)
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showNumberPad = !showNumberPad }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showNumberPad) "HIDE NUMPAD" else "SHOW NUMPAD (0-9)",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    if (showNumberPad) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }

            AnimatedVisibility(visible = showNumberPad) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Row 1-2-3
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in 1..3) {
                            TactileButton(
                                modifier = Modifier.weight(1f).height(44.dp),
                                label = "$i",
                                onClick = { sendKey("DIGIT_$i") }
                            )
                        }
                    }
                    // Row 4-5-6
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in 4..6) {
                            TactileButton(
                                modifier = Modifier.weight(1f).height(44.dp),
                                label = "$i",
                                onClick = { sendKey("DIGIT_$i") }
                            )
                        }
                    }
                    // Row 7-8-9
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in 7..9) {
                            TactileButton(
                                modifier = Modifier.weight(1f).height(44.dp),
                                label = "$i",
                                onClick = { sendKey("DIGIT_$i") }
                            )
                        }
                    }
                    // Row 0
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        TactileButton(
                            modifier = Modifier.fillMaxWidth(0.35f).height(44.dp),
                            label = "0",
                            onClick = { sendKey("DIGIT_0") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }

        // ==========================================
        // Active Dialogs
        // ==========================================
        if (showServiceHub) {
            ServiceMenuDialog(
                brand = selectedBrand,
                macroExecutor = macroExecutor,
                coroutineScope = coroutineScope,
                onTransmitButton = { btn ->
                    macroExecutor.transmitButton(btn, selectedBrand)
                },
                onDismiss = { showServiceHub = false }
            )
        }

        if (showBoardLookup) {
            MotherboardLookupDialog(
                onSelectCode = { code ->
                    // Auto-route or execute
                    showBoardLookup = false
                    showServiceHub = true
                },
                onDismiss = { showBoardLookup = false }
            )
        }

        if (showCustomHex) {
            CustomHexDialog(
                irBlasterManager = irBlasterManager,
                onDismiss = { showCustomHex = false }
            )
        }
    }
}
