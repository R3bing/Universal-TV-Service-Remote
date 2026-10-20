package com.servicetv.remote.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.servicetv.remote.ir.IrBlasterManager
import com.servicetv.remote.ir.ProntoHexConverter
import com.servicetv.remote.ui.theme.AccentCyan
import com.servicetv.remote.ui.theme.ButtonNeutralBorder
import com.servicetv.remote.ui.theme.DangerRed
import com.servicetv.remote.ui.theme.DarkBackground
import com.servicetv.remote.ui.theme.SuccessGreen
import com.servicetv.remote.ui.theme.SurfaceCard
import com.servicetv.remote.ui.theme.TextPrimary
import com.servicetv.remote.ui.theme.TextSecondary

@Composable
fun CustomHexDialog(
    irBlasterManager: IrBlasterManager,
    onDismiss: () -> Unit
) {
    var hexInput by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20.dp),
            color = DarkBackground,
            border = androidx.compose.foundation.BorderStroke(1.dp, AccentCyan)
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
                            text = "⚡ RAW HEX INJECTOR",
                            color = AccentCyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "Philips Pronto Hex / RAW IR Format",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = hexInput,
                    onValueChange = {
                        hexInput = it
                        resultMessage = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    placeholder = {
                        Text(
                            "Paste Pronto Hex string here...\nExample:\n0000 006d 0022 0002 0155 00aa 0015 ...",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentCyan,
                        unfocusedBorderColor = ButtonNeutralBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Decoded info preview
                val parsed = remember(hexInput) {
                    if (hexInput.isNotBlank()) ProntoHexConverter.hexToPattern(hexInput) else null
                }

                parsed?.let { (freq, pattern) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = SurfaceCard)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Frequency: $freq Hz | Pulses: ${pattern.size}",
                                color = SuccessGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(1f).height(48.dp),
                        onClick = {
                            if (hexInput.isBlank()) {
                                resultMessage = "Please enter a Pronto Hex code"
                                isSuccess = false
                                return@Button
                            }

                            val success = irBlasterManager.transmitProntoHex(hexInput)
                            if (success) {
                                resultMessage = "Signal transmitted successfully via IR!"
                                isSuccess = true
                            } else {
                                if (!irBlasterManager.hasIrEmitter()) {
                                    resultMessage = "Emulated: Transmitted (Device has no hardware IR blaster)"
                                    isSuccess = true
                                } else {
                                    resultMessage = "Failed: Invalid Pronto Hex format"
                                    isSuccess = false
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AccentCyan)
                    ) {
                        Text("TRANSMIT NOW", color = DarkBackground, fontWeight = FontWeight.Bold)
                    }
                }

                resultMessage?.let { msg ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = msg,
                        color = if (isSuccess) SuccessGreen else DangerRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
