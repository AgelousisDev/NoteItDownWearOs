package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material.Icon
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.noteItDown.ui.views.NoteRowView
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun NotesListScreenView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    transformingLazyColumnState: TransformingLazyColumnState,
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
    TransformingLazyColumn(
        modifier = modifier,
        state = transformingLazyColumnState,
        contentPadding = contentPadding
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
            NoteRowView(
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
            contentPadding = PaddingValues(
                all = 24.dp
            ),
            transformingLazyColumnState = rememberTransformingLazyColumnState(),
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