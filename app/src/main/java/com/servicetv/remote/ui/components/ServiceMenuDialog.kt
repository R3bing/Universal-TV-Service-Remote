package com.servicetv.remote.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.servicetv.remote.ir.MacroExecutor
import com.servicetv.remote.ir.MacroStatus
import com.servicetv.remote.model.RemoteButton
import com.servicetv.remote.model.ServiceMacro
import com.servicetv.remote.model.TvBrand
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ServiceMenuDialog(
    brand: TvBrand,
    macroExecutor: MacroExecutor,
    coroutineScope: CoroutineScope,
    onTransmitButton: (RemoteButton) -> Unit,
    onDismiss: () -> Unit
) {
    val macroStatus by macroExecutor.status.collectAsState()
    var pendingDangerButton by remember { mutableStateOf<RemoteButton?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20.dp),
            color = DarkBackground,
            border = androidx.compose.foundation.BorderStroke(1.dp, AccentAmber)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "⚡ SERVICE MODE HUB",
                            color = AccentAmber,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                        Text(
                            text = brand.name,
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Real-time Macro Transmission Status Bar
                AnimatedVisibility(visible = macroStatus !is MacroStatus.Idle) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceCardElevated),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentCyan)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            when (val st = macroStatus) {
                                is MacroStatus.Transmitting -> {
                                    Text(
                                        text = "Transmitting Step ${st.currentStep}/${st.totalSteps}: ${st.keyName}",
                                        color = AccentCyan,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    LinearProgressIndicator(
                                        progress = { st.currentStep.toFloat() / st.totalSteps.toFloat() },
                                        modifier = Modifier.fillMaxWidth(),
                                        color = AccentCyan
                                    )
                                }
                                is MacroStatus.Completed -> {
                                    Text(
                                        text = "✔ Sent: ${st.macroName}",
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                                is MacroStatus.Error -> {
                                    Text(
                                        text = "⚠ Error: ${st.message}",
                                        color = DangerRed,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                                else -> Unit
                            }
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Section 1: Discrete Factory Buttons (LG In-Start, Samsung Factory, etc.)
                    if (brand.discreteServiceButtons.isNotEmpty()) {
                        item {
                            Text(
                                text = "DISCRETE SERVICE KEYS (EXACT IR)",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                brand.discreteServiceButtons.forEach { btn ->
                                    TactileButton(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(54.dp),
                                        label = btn.label,
                                        subLabel = btn.subLabel,
                                        backgroundColor = if (btn.isDanger) DangerRed.copy(alpha = 0.2f) else AccentAmber.copy(alpha = 0.15f),
                                        borderColor = if (btn.isDanger) DangerRed else AccentAmber,
                                        textColor = if (btn.isDanger) DangerRed else AccentAmber,
                                        isDanger = btn.isDanger,
                                        onClick = {
                                            if (btn.isDanger) {
                                                pendingDangerButton = btn
                                            } else {
                                                onTransmitButton(btn)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Section 2: One-Tap Automated Macros
                    if (brand.serviceMacros.isNotEmpty()) {
                        item {
                            Text(
                                text = "AUTOMATED SEQUENCE MACROS",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }

                        items(brand.serviceMacros) { macro ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        coroutineScope.launch {
                                            macroExecutor.executeMacro(macro, brand)
                                        }
                                    },
                                colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ButtonNeutralBorder)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = macro.name,
                                            color = AccentCyan,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = macro.description,
                                            color = TextSecondary,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(AccentCyan.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                            .border(1.dp, AccentCyan, RoundedCornerShape(8.dp))
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "RUN",
                                            color = AccentCyan,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Section 3: Tested Factory PINs
                    if (brand.defaultPins.isNotEmpty()) {
                        item {
                            Text(
                                text = "DEFAULT FACTORY PASSWORDS / PINS",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                brand.defaultPins.forEach { pin ->
                                    Box(
                                        modifier = Modifier
                                            .background(SurfaceCardElevated, RoundedCornerShape(8.dp))
                                            .border(1.dp, ButtonNeutralBorder, RoundedCornerShape(8.dp))
                                            .padding(horizontal = 14.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = pin,
                                            color = AccentAmber,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Section 4: Instructions
                    if (brand.serviceInstructions.isNotBlank()) {
                        item {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SurfaceCardElevated),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Technician Note:",
                                        color = AccentAmber,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = brand.serviceInstructions,
                                        color = TextSecondary,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Safety Confirmation for destructive buttons like IN-STOP (LG Factory Reset)
    pendingDangerButton?.let { btn ->
        AlertDialog(
            onDismissRequest = { pendingDangerButton = null },
            icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = DangerRed) },
            title = { Text(text = "WARNING: ${btn.label}", color = DangerRed) },
            text = {
                Text(
                    text = "This command executes a deep hardware factory wipe/reset and may erase NVRAM settings. Are you sure you want to transmit this code?",
                    color = TextPrimary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onTransmitButton(btn)
                        pendingDangerButton = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = DangerRed)
                ) {
                    Text("TRANSMIT ANYWAY")
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDangerButton = null }) {
                    Text("CANCEL", color = TextSecondary)
                }
            },
            containerColor = DarkBackground
        )
    }
}
