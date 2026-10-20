package com.servicetv.remote.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF101216)
val SurfaceCard = Color(0xFF1A1D24)
val SurfaceCardElevated = Color(0xFF242832)

val AccentCyan = Color(0xFF00E5FF)
val AccentAmber = Color(0xFFFFB300)
val DangerRed = Color(0xFFFF3D00)
val SuccessGreen = Color(0xFF00E676)

val ButtonNeutral = Color(0xFF282D37)
val ButtonNeutralBorder = Color(0xFF38404E)
val TextPrimary = Color(0xFFEEEEEE)
val TextSecondary = Color(0xFF9E9E9E)

val DarkColorScheme = darkColorScheme(
    primary = AccentCyan,
    secondary = AccentAmber,
    tertiary = SuccessGreen,
    background = DarkBackground,
    surface = SurfaceCard,
    error = DangerRed,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)
