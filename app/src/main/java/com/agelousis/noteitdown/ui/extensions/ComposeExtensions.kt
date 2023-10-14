package com.agelousis.noteitdown.ui.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val Modifier.whiteRoundedBackgroundModifier
    get() = background(
        color = Color.White,
        shape = RoundedCornerShape(
            size = 16.dp
        )
    )