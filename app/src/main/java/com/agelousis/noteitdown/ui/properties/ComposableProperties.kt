package com.agelousis.noteitdown.ui.properties

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

val randomColor
    get() = Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat()
    )