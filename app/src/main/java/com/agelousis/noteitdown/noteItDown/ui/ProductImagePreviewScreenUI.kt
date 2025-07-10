package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices
import coil.compose.AsyncImage
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.extensions.imageRequest

@Composable
fun ProductImagePreviewScreenView(
    modifier: Modifier = Modifier,
    productImageUrl: String?
) {
    val context = LocalContext.current
    AsyncImage(
        modifier = modifier
            .fillMaxSize(),
        model = context imageRequest productImageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun ProductImagePreviewScreenViewPreview() {
    NoteItDownTheme {
        ProductImagePreviewScreenView(
            productImageUrl = ""
        )
    }
}