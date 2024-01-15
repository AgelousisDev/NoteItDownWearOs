package com.agelousis.noteitdown.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.ColorScheme

val Primary = Color(
    color = 0xFF90DFAA
)
val PrimaryContainer = Color(
    color = 0xFF90DFCC
)
val Secondary = Color(
    color = 0xFF9CDBF3
)
val SecondaryContainer = Color(
    color = 0xFF9CDBCC
)
val Red400 = Color(0xFFCF6679)

internal val wearColorPalette = ColorScheme(
    primary = Primary,
    primaryContainer = PrimaryContainer,
    secondary = Secondary,
    secondaryContainer = SecondaryContainer,
    error = Red400
)