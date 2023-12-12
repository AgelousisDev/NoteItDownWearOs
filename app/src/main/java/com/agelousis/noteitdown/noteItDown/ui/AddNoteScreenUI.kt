package com.agelousis.noteitdown.noteItDown.ui

import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

private const val NOTE_EXTRAS_KEY = "noteKey"
private const val TAG_EXTRAS_KEY = "tagKey"

typealias ButtonBlock = () -> Unit

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddNoteScreenLayout(
    scalingLazyColumnState: androidx.wear.compose.foundation.lazy.ScalingLazyListState,
    notesListBlock: ButtonBlock,
    methodOfThreeBlock: ButtonBlock,
    productsWithQuantityBlock: ButtonBlock
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStorageHelper = PreferencesStoreHelper(
        context = context
    )
    var note by remember {
        mutableStateOf<String?>(value = null)
    }
    var tag by remember {
        mutableStateOf<String?>(value = null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        activityResult.data?.let { data ->
            val results = RemoteInput.getResultsFromIntent(data)
            when {
                results.containsKey(NOTE_EXTRAS_KEY) ->
                    note = results?.getCharSequence(NOTE_EXTRAS_KEY) as? String
                results.containsKey(TAG_EXTRAS_KEY) ->
                    tag = results?.getCharSequence(TAG_EXTRAS_KEY) as? String
            }
        }
    }
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
        state = scalingLazyColumnState,
    ) {
        item {
            Text(
                text = stringResource(
                    id = R.string.app_name
                ),
                style = MaterialTheme.typography.body1
            )
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                ),
                modifier = Modifier
                    .padding(
                        top = 16.dp
                    )
            ) {
                Chip(
                    label = {
                        Text(
                            text = tag ?: stringResource(
                                id = R.string.key_add_tag_here
                            ),
                            style = MaterialTheme.typography.caption2
                        )

                    },
                    onClick = {
                        launcher.launch(
                            getRemoteIntentInput(
                                context = context,
                                extrasKey = TAG_EXTRAS_KEY
                            )
                        )
                    },
                    modifier = Modifier
                        .height(
                            height = 35.dp
                        )
                )
                Chip(
                    label = {
                        Text(
                            text = note ?: stringResource(
                                id = R.string.key_add_note_label
                            ),
                            style = MaterialTheme.typography.caption2
                        )
                    },
                    onClick = {
                        launcher.launch(
                            getRemoteIntentInput(
                                context = context,
                                extrasKey = NOTE_EXTRAS_KEY
                            )
                        )
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ),
                    modifier = Modifier
                        .height(
                            height = 35.dp
                        )
                )
            }
        }
        item {
            FlowRow {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            preferencesStorageHelper addNote NoteDataModel(
                                tag = tag,
                                note = note
                            )
                            tag = null
                            note = null
                        }
                    },
                    enabled = !tag.isNullOrEmpty() && !note.isNullOrEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Button(
                    onClick = notesListBlock,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Button(
                    onClick = methodOfThreeBlock,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Calculate,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Button(
                    onClick = productsWithQuantityBlock,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.MonitorWeight,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

private fun getRemoteIntentInput(
    context: Context,
    extrasKey: String
): Intent {
    val intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
    val remoteInputs = listOf(
        RemoteInput.Builder(extrasKey)
            .setLabel(context.resources.getString(R.string.key_add_note_label))
            .wearableExtender {
                setEmojisAllowed(false)
                setInputActionType(EditorInfo.IME_ACTION_DONE)
            }.build()
    )
    RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)
    return intent
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun AddNoteScreenLayoutPreview() {
    NoteItDownTheme {
        AddNoteScreenLayout(
            scalingLazyColumnState = rememberScalingLazyListState(),
            notesListBlock = {},
            methodOfThreeBlock = {},
            productsWithQuantityBlock = {}
        )
    }
}