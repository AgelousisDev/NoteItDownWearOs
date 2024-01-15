package com.agelousis.noteitdown.noteItDown.ui

import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.bold
import com.agelousis.noteitdown.ui.theme.withColor
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

private const val NOTE_EXTRAS_KEY = "noteKey"
private const val TAG_EXTRAS_KEY = "tagKey"

typealias ButtonBlock = () -> Unit

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddNoteScreenLayout(
    scalingLazyColumnState: ScalingLazyListState,
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
                            style = MaterialTheme.typography.labelMedium
                                withColor Color.Black
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
                            style = MaterialTheme.typography.labelMedium
                                withColor Color.Black
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
                        backgroundColor = MaterialTheme.colorScheme.secondary
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
                IconButton(
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
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = null,
                        tint = Color.Green.copy(
                            alpha = 0.5f
                        )
                    )
                }
                IconButton(
                    onClick = notesListBlock
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null,
                        tint = Color.Yellow.copy(
                            alpha = 0.5f
                        )
                    )
                }
                IconButton(
                    onClick = methodOfThreeBlock
                ) {
                    Icon(
                        imageVector = Icons.Filled.Calculate,
                        contentDescription = null,
                        tint = Color.Cyan.copy(
                            alpha = 0.5f
                        )
                    )
                }
                IconButton(
                    onClick = productsWithQuantityBlock
                ) {
                    Icon(
                        imageVector = Icons.Filled.MonitorWeight,
                        contentDescription = null,
                        tint = Color.Red.copy(
                            alpha = 0.5f
                        )
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