package com.servicetv.remote.ui.components

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
import androidx.compose.material.icons.filled.Search
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
import com.servicetv.remote.ui.theme.AccentCyan
import com.servicetv.remote.ui.theme.AccentAmber
import com.servicetv.remote.ui.theme.ButtonNeutralBorder
import com.servicetv.remote.ui.theme.DarkBackground
import com.servicetv.remote.ui.theme.SurfaceCard
import com.servicetv.remote.ui.theme.SurfaceCardElevated
import com.servicetv.remote.ui.theme.TextPrimary
import com.servicetv.remote.ui.theme.TextSecondary

data class MotherboardItem(
    val boardNumber: String,
    val chipset: String,
    val serviceCode: String,
    val mirrorPath: String,
    val lvdsPath: String,
    val notes: String
)

val MotherboardCatalog = listOf(
    MotherboardItem(
        boardNumber = "TP.MS338.PC821 / PB801",
        chipset = "MStar 6A338 (Android Smart)",
        serviceCode = "MENU + 1 1 4 7",
        mirrorPath = "Special Set -> Panel Settings -> Mirror",
        lvdsPath = "Panel Settings -> TI Mode (JEIDA/VESA)",
        notes = "Very common in Vu, Micromax, Shinco 32/40/43 inch LED TVs."
    ),
    MotherboardItem(
        boardNumber = "CV338H-A42 / U42 / T42",
        chipset = "CVTE Realtek (Smart Android)",
        serviceCode = "INPUT + 2 5 8 0",
        mirrorPath = "Design Menu -> Panel Setting -> Mirror Enable",
        lvdsPath = "Panel Setting -> LVDS Map (8-bit / 6-bit)",
        notes = "Powers cloudwalker, Daiwa, assembled 4K/FHD smart TVs."
    ),
    MotherboardItem(
        boardNumber = "T.VST59.031 / PB819",
        chipset = "TSUMV59XU (Non-Smart)",
        serviceCode = "MENU + 1 1 4 7",
        mirrorPath = "Panel Adjust -> Mirror -> On/Off",
        lvdsPath = "Panel Adjust -> LVDS Map",
        notes = "The #1 universal replacement card used across Indian TV repair shops."
    ),
    MotherboardItem(
        boardNumber = "VS.TP53U61.2 / U71.2",
        chipset = "HiSilicon / AllWinner (Android)",
        serviceCode = "INPUT + 2 0 8",
        mirrorPath = "System Setting -> Panel Config -> Flip",
        lvdsPath = "Panel Config -> Bit Mode",
        notes = "Used in low-cost Android 9/11 combo boards."
    ),
    MotherboardItem(
        boardNumber = "TP.SK506.PB801 / PC821",
        chipset = "MStar 6683 (Android 9/11/12)",
        serviceCode = "INPUT + 8 1 5 or MENU + 1 1 4 7",
        mirrorPath = "Factory Menu -> Panel Setting -> Flip Mode",
        lvdsPath = "Panel Setting -> Color Format",
        notes = "Widely used in modern 32/43 frameless LED TVs."
    ),
    MotherboardItem(
        boardNumber = "CV56BH-Q42 / CVMV26L",
        chipset = "MStar V56 (Non-Smart HD)",
        serviceCode = "INPUT + 2 5 8 0 or MENU + 1 1 4 7",
        mirrorPath = "General Setting -> Panel -> Flip",
        lvdsPath = "General Setting -> LVDS Format",
        notes = "Standard HD Ready non-smart board."
    ),
    MotherboardItem(
        boardNumber = "TP.HV320.PB801",
        chipset = "MStar V59 (Comb / Inverter on-board)",
        serviceCode = "MENU + 1 1 4 7",
        mirrorPath = "Panel Settings -> Mirror Image",
        lvdsPath = "Panel Settings -> LVDS Map Mode",
        notes = "3-in-1 combo board (Power + Backlight Inverter + Motherboard)."
    ),
    MotherboardItem(
        boardNumber = "T.V56.03 / SKR.03",
        chipset = "TSUMV56 (Multi-Resolution)",
        serviceCode = "MENU + 1 1 4 7",
        mirrorPath = "Panel Setting -> Mirror",
        lvdsPath = "Panel Setting -> Map",
        notes = "Supports USB firmware flashing for resolution switching."
    )
)

@Composable
fun MotherboardLookupDialog(
    onSelectCode: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredItems = remember(searchQuery) {
        if (searchQuery.isBlank()) MotherboardCatalog
        else MotherboardCatalog.filter {
            it.boardNumber.contains(searchQuery, ignoreCase = true) ||
            it.chipset.contains(searchQuery, ignoreCase = true) ||
            it.serviceCode.contains(searchQuery, ignoreCase = true)
        }
    }

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
                            text = "🔍 CHINESE BOARD DATABASE",
                            color = AccentCyan,
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "Find Service Code by Motherboard Model",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search (e.g. TP.MS338, CV338, 59, 56)...", color = TextSecondary, fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = AccentCyan) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentCyan,
                        unfocusedBorderColor = ButtonNeutralBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredItems) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectCode(item.serviceCode) },
                            colors = CardDefaults.cardColors(containerColor = SurfaceCard),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ButtonNeutralBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.boardNumber,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Box(
                                        modifier = Modifier
                                            .border(1.dp, AccentAmber, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = item.serviceCode,
                                            color = AccentAmber,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }

                                Text(
                                    text = item.chipset,
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "🔄 Mirror Fix: ${item.mirrorPath}",
                                    color = AccentCyan,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "🎨 LVDS Fix: ${item.lvdsPath}",
                                    color = AccentCyan,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "ℹ ${item.notes}",
                                    color = TextSecondary,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
