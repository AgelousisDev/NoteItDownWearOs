package com.agelousis.noteitdown.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.dynamicColorScheme

@Composable
fun NoteItDownTheme(
    content: @Composable () -> Unit
) {
    val dynamicColorScheme = dynamicColorScheme(
        context = LocalContext.current
    )
    MaterialTheme(
        typography = Typography,
        // For shapes, we generally recommend using the default Material Wear shapes which are
        // optimized for round and non-round devices.
        colorScheme = dynamicColorScheme
            ?: ColorScheme(),
        content = content
    )
}