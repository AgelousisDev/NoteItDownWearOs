package com.agelousis.noteitdown.noteItDown.ui

import android.app.RemoteInput
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.ui.theme.Typography
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import com.agelousis.noteitdown.models.NoteDataModel
import kotlinx.coroutines.launch

private const val NOTE_EXTRAS_KEY = "noteKey"

@Composable
fun NoteItDownActivityLayout() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStorageHelper = PreferencesStoreHelper(
        context = context
    )
    var note by remember {
        mutableStateOf<String?>(value = null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        activityResult.data?.let { data ->
            val results = RemoteInput.getResultsFromIntent(data)
            note = results?.getCharSequence(NOTE_EXTRAS_KEY) as? String
                ?: ""

        }
    }
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 32.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.Bottom,
        state = rememberScalingLazyListState()
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp
                )
            ) {
                Text(
                    text = stringResource(
                        id = R.string.app_name
                    ),
                    style = Typography.body1
                )
                if (!note.isNullOrEmpty())
                    Chip(
                        label = {
                            Text(
                                text = note
                                    ?: "",
                                style = Typography.caption1
                            )
                        },
                        onClick = {}
                    )
                Chip(
                    label = {
                        Text(
                            text = stringResource(
                                id = R.string.key_add_note_label
                            ),
                            style = Typography.caption2
                        )

                    },
                    onClick = {
                        val intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
                        val remoteInputs = listOf(
                            RemoteInput.Builder(NOTE_EXTRAS_KEY)
                                .setLabel(context.resources.getString(R.string.key_add_note_label))
                                .wearableExtender {
                                    setEmojisAllowed(false)
                                    setInputActionType(EditorInfo.IME_ACTION_DONE)
                                }.build()
                        )
                        RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)
                        launcher.launch(intent)
                    }
                )
                Button(
                    onClick = {
                        coroutineScope.launch {
                            preferencesStorageHelper addNote NoteDataModel(
                                tag = "1",
                                note = note
                            )
                            note = null
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    NoteItDownActivityLayout()
}