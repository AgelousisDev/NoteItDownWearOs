package com.agelousis.noteitdown.noteItDown.ui.views

import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.CompactButton
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.network.SuccessBlock
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.withTextAlign

private const val NOTE_EXTRAS_KEY = "noteKey"
private const val TAG_EXTRAS_KEY = "tagKey"

@Composable
fun EnterTagView(
    modifier: Modifier = Modifier,
    tagState: String?,
    noteState: String?,
    writingTag: SuccessBlock<String?>,
    writingNote: SuccessBlock<String?>
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val screenWidth = LocalWindowInfo.current.containerSize.width
    val launcher = tagLauncher(
        writingNote = writingNote,
        writingTag = writingTag
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompactButton(
            modifier = Modifier
                .size(
                    width = with(
                        receiver = density
                    ) {
                        screenWidth.toDp() / 2
                    },
                    height = 50.dp
                ),
            onClick = {
                launcher.launch(
                    getRemoteIntentInput(
                        context = context,
                        extrasKey = TAG_EXTRAS_KEY
                    )
                )
            }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = tagState
                    ?: stringResource(
                        id = R.string.key_add_tag_here
                    ),
                style = MaterialTheme.typography.labelMedium
                    withTextAlign TextAlign.Center
            )
        }
        CompactButton(
            modifier = Modifier
                .size(
                    width = with(
                        receiver = density
                    ) {
                        screenWidth.toDp() / 2
                    },
                    height = 50.dp
                ),
            onClick = {
                launcher.launch(
                    getRemoteIntentInput(
                        context = context,
                        extrasKey = NOTE_EXTRAS_KEY
                    )
                )
            }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = noteState
                    ?: stringResource(
                        id = R.string.key_add_note_label
                    ),
                style = MaterialTheme.typography.labelMedium
                    withTextAlign TextAlign.Center
            )
        }
    }
}

@Composable
private fun tagLauncher(
    writingNote: SuccessBlock<String?>,
    writingTag: SuccessBlock<String?>
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
) { activityResult ->
    activityResult.data?.let { data ->
        val results = RemoteInput.getResultsFromIntent(data)
        when {
            results.containsKey(NOTE_EXTRAS_KEY) ->
                writingNote(results?.getCharSequence(NOTE_EXTRAS_KEY) as? String)
            results.containsKey(TAG_EXTRAS_KEY) ->
                writingTag(results?.getCharSequence(TAG_EXTRAS_KEY) as? String)
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
fun EnterTagViewPreview() {
    NoteItDownTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            EnterTagView(
                tagState = "Tag",
                noteState = "Note",
                writingTag = {},
                writingNote = {}
            )
        }
    }
}