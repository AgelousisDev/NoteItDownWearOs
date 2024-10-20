package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Icon
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.noteItDown.ui.rows.NoteRowLayout
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun NotesListScreenView(
    scalingLazyColumnState: androidx.wear.compose.foundation.lazy.ScalingLazyListState,
    noteDataModelListForPreview: List<NoteDataModel>? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStoreHelper = PreferencesStoreHelper(
        context = context
    )
    val noteDataModelList by preferencesStoreHelper.noteDataModelList.collectAsState(
        initial = noteDataModelListForPreview ?: listOf()
    )
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Bottom
        ),
        state = scalingLazyColumnState,
    ) {
        if (noteDataModelList.isNullOrEmpty())
            item {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = Icons.Filled.Image.name,
                    modifier = Modifier
                        .size(
                            size = 50.dp
                        )
                )
            }
        items(
            items = noteDataModelList
                ?: listOf()
        ) { noteDataModel ->
            NoteRowLayout(
                noteDataModel = noteDataModel,
                noteDataModelBlock = {
                    coroutineScope.launch {
                        preferencesStoreHelper setNoteAsFirst noteDataModel
                    }
                },
                noteDataRemovalBlock = {
                    coroutineScope.launch {
                        preferencesStoreHelper removeNote noteDataModel
                    }
                }
            )
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun NotesListScreenViewPreview() {
    NoteItDownTheme {
        NotesListScreenView(
            scalingLazyColumnState = androidx.wear.compose.foundation.lazy.rememberScalingLazyListState(),
            noteDataModelListForPreview = listOf(
                NoteDataModel(
                    tag = "First Tag",
                    note = "First Note"
                ),
                NoteDataModel(
                    tag = "Second Tag",
                    note = "Second Note"
                ),
                NoteDataModel(
                    tag = "Third Tag",
                    note = "Third Note"
                )
            )
        )
    }
}