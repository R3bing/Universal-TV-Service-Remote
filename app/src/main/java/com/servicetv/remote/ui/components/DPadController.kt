package com.servicetv.remote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.servicetv.remote.ui.theme.AccentCyan
import com.servicetv.remote.ui.theme.ButtonNeutral
import com.servicetv.remote.ui.theme.ButtonNeutralBorder
import com.servicetv.remote.ui.theme.SurfaceCardElevated
import com.servicetv.remote.ui.theme.TextPrimary

@Composable
fun DPadController(
    modifier: Modifier = Modifier,
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onOk: () -> Unit
) {
    Box(
        modifier = modifier
            .size(190.dp)
            .clip(CircleShape)
            .background(SurfaceCardElevated)
            .border(2.dp, ButtonNeutralBorder, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Up Button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
        ) {
            TactileButton(
                modifier = Modifier.width(64.dp).height(44.dp),
                label = "▲",
                onClick = onUp
            )
        }

        // Down Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        ) {
            TactileButton(
                modifier = Modifier.width(64.dp).height(44.dp),
                label = "▼",
                onClick = onDown
            )
        }

        // Left Button
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
        ) {
            TactileButton(
                modifier = Modifier.width(44.dp).height(64.dp),
                label = "◄",
                onClick = onLeft
            )
        }

        // Right Button
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        ) {
            TactileButton(
                modifier = Modifier.width(44.dp).height(64.dp),
                label = "►",
                onClick = onRight
            )
        }

        // Center OK Button
        TactileButton(
            modifier = Modifier.size(56.dp),
            label = "OK",
            backgroundColor = AccentCyan.copy(alpha = 0.15f),
            borderColor = AccentCyan,
            textColor = AccentCyan,
            cornerRadius = 28.dp,
            onClick = onOk
        )
    }
}
