package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.*
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.noteItDown.ui.rows.NoteRowLayout
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun NotesListScreenLayout(
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
            .fillMaxSize()
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = CircleShape
            ),
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
                    contentDescription = null,
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

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NotesListScreenLayoutPreview() {
    NoteItDownTheme {
        NotesListScreenLayout(
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