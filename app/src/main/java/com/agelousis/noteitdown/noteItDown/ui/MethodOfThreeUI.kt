package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.Purple700
import com.agelousis.noteitdown.utils.extensions.asIntValue

@Composable
fun MethodOfThreeLayout(
    scalingLazyColumnState: ScalingLazyListState
) {
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
        mutableStateOf(value = "100")
    }
    var startingPointTargetValue by remember {
        mutableStateOf(value = "50")
    }
    var endingPointValue by remember {
        mutableStateOf(value = "200")
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
            PointChipValue(
                rowScope = this,
                label = "x =",
                value = startingPointValue,
                valueBlock = {
                    startingPointValue = it
                }
            )
            // Starting point target value
            PointChipValue(
                rowScope = this,
                label = "y =",
                value = startingPointTargetValue,
                valueBlock = {
                    startingPointTargetValue = it
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
            PointChipValue(
                rowScope = this,
                label = "z = ",
                value = endingPointValue,
                valueBlock = {
                    endingPointValue = it
                }
            )
            // Ending point target value
            ResultChipValue(
                rowScope = this,
                result = getMethodOfThreeResult(
                    startingPointValue = startingPointValue.toFloatOrNull(),
                    startingPointTargetValue = startingPointTargetValue.toFloatOrNull(),
                    endingPointValue = endingPointValue.toFloatOrNull()
                )
            )
        }
        Text(
            text = "(z * y) / x = ?",
            style = MaterialTheme.typography.caption3
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PointChipValue(
    rowScope: RowScope,
    label: String,
    value: String,
    valueBlock: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var onDoneState by remember {
        mutableStateOf(value = false)
    }
    rowScope.apply {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp
            ),
            modifier = Modifier
                .weight(
                    weight = 0.30f
                )
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.caption2
            )
            Box(
                modifier = Modifier
                    .height(
                        height = 50.dp
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primaryVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = {
                        if (!onDoneState)
                            valueBlock(it)
                        onDoneState = false
                    },
                    textStyle = MaterialTheme.typography.caption2.merge(
                        other = TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onDoneState = true
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    }
}

@Composable
private fun ResultChipValue(
    rowScope: RowScope,
    result: String
) {
    rowScope.apply {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 4.dp
            ),
            modifier = Modifier
                .weight(
                    weight = 0.30f
                )
        ) {
            Text(
                text = "? =",
                style = MaterialTheme.typography.caption2
            )
            Chip(
                modifier = Modifier
                    .weight(
                        weight = 0.25f
                    )
                    .height(
                        height = 50.dp
                    ),
                label = {
                    Text(
                        text = result,
                        style = MaterialTheme.typography.caption2,
                        textAlign = TextAlign.Center
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

private fun getMethodOfThreeResult(
    startingPointValue: Float?,
    startingPointTargetValue: Float?,
    endingPointValue: Float?
) = arrayOf(
    startingPointValue,
    startingPointTargetValue,
    endingPointValue
).mapNotNull { value ->
    value
}.takeIf { values ->
    values.size == 3
}?.let { values ->
    ((values[2] * values[1]) / values[0]).asIntValue
} ?: ""

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MethodOfThreeLayoutPreview() {
    NoteItDownTheme {
        MethodOfThreeLayout(
            scalingLazyColumnState = rememberScalingLazyListState()
        )
    }
}