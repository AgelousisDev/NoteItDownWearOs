package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.scrollAway
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.noteItDown.NoteItDownActivityNavigation
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme

@Composable
fun NoteItDownActivityLayout() {
    val scalingLazyColumnState = androidx.wear.compose.foundation.lazy.rememberScalingLazyListState()
    Scaffold(
        timeText = {
            TimeText(
                modifier = Modifier
                    .scrollAway(
                        scrollState = scalingLazyColumnState
                    )
            )
        },
        vignette = {
            Vignette(
                vignettePosition = VignettePosition.TopAndBottom
            )
        }
    ) {
        NoteItDownActivityNavigation(
            scalingLazyColumnState = scalingLazyColumnState
        )
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun NoteItDownActivityLayoutPreview() {
    NoteItDownTheme {
        NoteItDownActivityLayout()
    }
}