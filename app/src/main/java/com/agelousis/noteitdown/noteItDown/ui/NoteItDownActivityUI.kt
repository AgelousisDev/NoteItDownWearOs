package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.noteItDown.NoteItDownActivityNavigation
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme

@Composable
fun NoteItDownActivityView() {
    val viewModel = viewModel<NoteItDownBaseViewModel>()
    val transformingLazyColumnState = rememberTransformingLazyColumnState()
    ScreenScaffold(
        scrollState = transformingLazyColumnState,
        contentPadding = PaddingValues(
            all = 24.dp
        )
    ) { contentPadding ->
        NoteItDownActivityNavigation(
            contentPadding = contentPadding,
            viewModel = viewModel,
            transformingLazyColumnState = transformingLazyColumnState
        )
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun NoteItDownActivityLayoutView() {
    NoteItDownTheme {
        NoteItDownActivityView()
    }
}