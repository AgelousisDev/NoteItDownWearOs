package com.agelousis.noteitdown.ui.properties

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

val randomColor
    get() = Color(
        red = Random.nextDouble(
            from = 0.26,
            until = 0.35
        ).toFloat(),
        green = Random.nextDouble(
            from = 0.26,
            until = 0.35
        ).toFloat(),
        blue = Random.nextDouble(
            from = 0.26,
            until = 0.35
        ).toFloat(),
        alpha = Random.nextDouble(
            from = 0.5,
            until = 1.0
        ).toFloat()
    )

