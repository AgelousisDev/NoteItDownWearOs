package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.noteItDown.enumerations.NoteItDownManagementChip
import com.agelousis.noteitdown.noteItDown.ui.views.EnterTagView
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.bold
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

typealias ButtonBlock = () -> Unit

@Composable
fun AddNoteScreenView(
    scalingLazyColumnState: ScalingLazyListState,
    notesListBlock: ButtonBlock,
    methodOfThreeBlock: ButtonBlock,
    productsWithQuantityBlock: ButtonBlock
) {
    val context = LocalContext.current
    val resources = LocalResources.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStorageHelper = PreferencesStoreHelper(
        context = context
    )
    val (noteState, writingNote) = remember {
        mutableStateOf<String?>(value = null)
    }
    val (tagState, writingTag) = remember {
        mutableStateOf<String?>(value = null)
    }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        state = scalingLazyColumnState,
    ) {
        item {
            Text(
                text = stringResource(
                    id = R.string.app_name
                ),
                style = MaterialTheme.typography.bodyLarge.bold
            )
        }
        item {
            EnterTagView(
                modifier = Modifier
                    .padding(
                        top = 16.dp
                    ),
                tagState = tagState,
                noteState = noteState,
                writingTag = writingTag,
                writingNote = writingNote
            )
        }
        items(
            items = NoteItDownManagementChip.entries
        ) { noteItDownManagementChip ->
            Chip(
                onClick = {
                    noteItDownManagementChip.action(
                        saveBlock = {
                            coroutineScope.launch {
                                preferencesStorageHelper addNote NoteDataModel(
                                    tag = tagState,
                                    note = noteState
                                )
                                writingTag(null)
                                writingNote(null)
                            }
                        },
                        notesListBlock = notesListBlock,
                        methodOfThreeBlock = methodOfThreeBlock,
                        productsWithQuantityBlock = productsWithQuantityBlock
                    )
                },
                label = {
                    Text(
                        text = noteItDownManagementChip label resources,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                icon = {
                    Icon(
                        imageVector = noteItDownManagementChip.icon,
                        contentDescription = noteItDownManagementChip.name,
                        tint = noteItDownManagementChip.tint
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.White.copy(
                        alpha = .2f
                    )
                )
            )
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun AddNoteScreenViewPreview() {
    NoteItDownTheme {
        AddNoteScreenView(
            scalingLazyColumnState = rememberScalingLazyListState(),
            notesListBlock = {},
            methodOfThreeBlock = {},
            productsWithQuantityBlock = {}
        )
    }
}