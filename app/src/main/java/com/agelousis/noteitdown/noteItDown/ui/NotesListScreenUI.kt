package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.noteItDown.ui.rows.NoteRowLayout
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper

@Composable
fun NotesListScreenLayout(
    noteDataModelListForPreview: List<NoteDataModel>? = null
) {
    val context = LocalContext.current
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
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        state = rememberScalingLazyListState(),
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
                noteDataModel = noteDataModel
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NotesListScreenLayoutPreview() {
    NotesListScreenLayout(
        noteDataModelListForPreview = listOf(
            NoteDataModel(
                tag = "Sample Tag",
                note = "Sample Note"
            )
        )
    )
}