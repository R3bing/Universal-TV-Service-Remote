package com.servicetv.remote.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.servicetv.remote.ui.theme.ButtonNeutral
import com.servicetv.remote.ui.theme.ButtonNeutralBorder
import com.servicetv.remote.ui.theme.DangerRed
import com.servicetv.remote.ui.theme.TextPrimary
import com.servicetv.remote.ui.theme.TextSecondary

@Composable
fun TactileButton(
    modifier: Modifier = Modifier,
    label: String,
    subLabel: String? = null,
    backgroundColor: Color = ButtonNeutral,
    borderColor: Color = ButtonNeutralBorder,
    textColor: Color = TextPrimary,
    cornerRadius: Dp = 12.dp,
    isDanger: Boolean = false,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isPressed) 0.92f else 1.0f, label = "button_scale")

    val finalBgColor = if (isDanger) DangerRed.copy(alpha = 0.2f) else backgroundColor
    val finalBorderColor = if (isDanger) DangerRed else borderColor
    val finalTextColor = if (isDanger) DangerRed else textColor

    Surface(
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            },
        shape = RoundedCornerShape(cornerRadius),
        color = finalBgColor,
        border = BorderStroke(1.dp, finalBorderColor),
        shadowElevation = if (isPressed) 1.dp else 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = label,
                    color = finalTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                if (!subLabel.isNullOrBlank()) {
                    Text(
                        text = subLabel,
                        color = TextSecondary,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
