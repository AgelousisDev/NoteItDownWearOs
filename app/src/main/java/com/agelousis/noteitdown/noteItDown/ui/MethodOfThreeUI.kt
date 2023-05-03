package com.agelousis.noteitdown.noteItDown.ui

import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.Purple700
import com.agelousis.noteitdown.utils.extensions.asIntValue

private val methodOfThreeValueKeys = arrayOf(
    "startingPointValue",
    "startingPointTargetValue",
    "endingPointValue"
)

@Composable
fun MethodOfThreeLayout(
    scalingLazyColumnState: ScalingLazyListState
) {
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = CircleShape
            ),
        contentPadding = PaddingValues(
            vertical = 32.dp,
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        state = scalingLazyColumnState,
    ) {
        item {
            Text(
                text = stringResource(
                    id = R.string.key_method_of_three_label
                ),
                style = MaterialTheme.typography.body1
            )
        }
        item {
            MethodOfThreeProcessLayout()
        }
    }
}

@Composable
private fun MethodOfThreeProcessLayout() {
    var startingPointValue by remember {
        mutableStateOf(value = 100f)
    }
    var startingPointTargetValue by remember {
        mutableStateOf(value = 50f)
    }
    var endingPointValue by remember {
        mutableStateOf(value = 0f)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        activityResult.data?.let { data ->
            val results = RemoteInput.getResultsFromIntent(data)
            when {
                results.containsKey(methodOfThreeValueKeys[0]) ->
                    startingPointValue = (results?.getCharSequence(methodOfThreeValueKeys[0]) as? String)?.toFloatOrNull()
                        ?: 100f
                results.containsKey(methodOfThreeValueKeys[1]) ->
                    startingPointTargetValue = (results?.getCharSequence(methodOfThreeValueKeys[1]) as? String)?.toFloatOrNull()
                        ?: 50f
                results.containsKey(methodOfThreeValueKeys[2]) ->
                    endingPointValue = (results?.getCharSequence(methodOfThreeValueKeys[2]) as? String)?.toFloatOrNull()
                        ?: 0f
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp
            )
        ) {
            // Starting point value
            Chip(
                modifier = Modifier
                    .weight(
                        weight = 0.40f
                    )
                    .height(
                        height = 50.dp
                    ),
                label = {
                    Text(
                        text = startingPointValue.asIntValue,
                        style = MaterialTheme.typography.caption2
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Transparent
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Purple700
                    )
                ),
                onClick = {
                    launcher.launch(
                        getRemoteIntentInput(
                            extrasKey = methodOfThreeValueKeys[0]
                        )
                    )
                }
            )
            // Starting point target value
            Chip(
                modifier = Modifier
                    .weight(
                        weight = 0.40f
                    )
                    .height(
                        height = 50.dp
                    ),
                label = {
                    Text(
                        text = startingPointTargetValue.asIntValue,
                        style = MaterialTheme.typography.caption2
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Transparent
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Purple700
                    )
                ),
                onClick = {
                    launcher.launch(
                        getRemoteIntentInput(
                            extrasKey = methodOfThreeValueKeys[1]
                        )
                    )
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp
            )
        ) {
            // Ending point value
            Chip(
                modifier = Modifier
                    .weight(
                        weight = 0.40f
                    )
                    .height(
                        height = 50.dp
                    ),
                label = {
                    Text(
                        text = endingPointValue.asIntValue,
                        style = MaterialTheme.typography.caption2
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.Transparent
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Purple700
                    )
                ),
                onClick = {
                    launcher.launch(
                        getRemoteIntentInput(
                            extrasKey = methodOfThreeValueKeys[2]
                        )
                    )
                }
            )
            // Ending point target value
            Chip(
                modifier = Modifier
                    .weight(
                        weight = 0.40f
                    )
                    .height(
                        height = 50.dp
                    ),
                label = {
                    Text(
                        text = ((endingPointValue * startingPointTargetValue) / startingPointValue).asIntValue,
                        style = MaterialTheme.typography.caption2
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Purple700
                    )
                ),
                onClick = {}
            )
        }
    }
}

private fun getRemoteIntentInput(
    extrasKey: String
): Intent {
    val intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
    val remoteInputs = listOf(
        RemoteInput.Builder(extrasKey)
            .wearableExtender {
                setEmojisAllowed(false)
                setInputActionType(EditorInfo.IME_ACTION_DONE)
            }.build()
    )
    RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)
    return intent
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MethodOfThreeLayoutPreview() {
    NoteItDownTheme {
        MethodOfThreeLayout(
            scalingLazyColumnState = rememberScalingLazyListState()
        )
    }
}