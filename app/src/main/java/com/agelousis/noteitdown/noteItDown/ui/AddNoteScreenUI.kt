package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material3.IconButton
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.noteItDown.ui.views.EnterTagView
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun AddNoteScreenView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    transformingLazyColumnState: TransformingLazyColumnState
) {
    val context = LocalContext.current
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
    TransformingLazyColumn(
        modifier = modifier,
        state = transformingLazyColumnState,
        contentPadding = contentPadding
    ) {
        item {
            EnterTagView(
                tagState = tagState,
                noteState = noteState,
                writingTag = writingTag,
                writingNote = writingNote
            )
        }
        item {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        preferencesStorageHelper addNote NoteDataModel(
                            tag = tagState,
                            note = noteState
                        )
                        writingTag(null)
                        writingNote(null)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun AddNoteScreenViewPreview() {
    NoteItDownTheme {
        AddNoteScreenView(
            contentPadding = PaddingValues(
                all = 24.dp
            ),
            transformingLazyColumnState = rememberTransformingLazyColumnState()
        )
    }
}