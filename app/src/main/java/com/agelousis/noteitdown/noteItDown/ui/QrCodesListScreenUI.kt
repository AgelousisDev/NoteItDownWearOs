package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.noteItDown.enumerations.QrCodeType
import com.agelousis.noteitdown.noteItDown.ui.rows.NoteRowLayout
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme

@Composable
fun QrCodesListScreenLayout(
    scalingLazyColumnState: ScalingLazyListState,
    noteDataModelListForPreview: List<NoteDataModel>? = null
) {
    val context = LocalContext.current
    val noteDataModelList = QrCodeType getNoteDataModelList context
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = CircleShape
            ),
        contentPadding = PaddingValues(
            vertical = 32.dp,
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Bottom
        ),
        state = scalingLazyColumnState,
    ) {
        if (noteDataModelList.isEmpty())
            item {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            size = 50.dp
                        )
                )
            }
        items(
            items = noteDataModelList
        ) { noteDataModel ->
            NoteRowLayout(
                noteDataModel = noteDataModel,
                noteDataModelBlock = {

                },
                noteDataRemovalBlock = {

                }
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun QrCodesListScreenUIPreview() {
    NoteItDownTheme {
        QrCodesListScreenLayout(
            scalingLazyColumnState = rememberScalingLazyListState(),
            noteDataModelListForPreview = QrCodeType.values().map { qrCodeType ->
                NoteDataModel(
                    icon = Icons.Filled.QrCode,
                    tag = qrCodeType.name,
                    note = qrCodeType getLabelWith LocalContext.current
                )
            }
        )
    }
}