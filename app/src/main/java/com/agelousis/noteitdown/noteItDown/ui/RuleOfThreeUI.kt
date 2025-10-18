package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.ui.composableView.BorderedTextField
import com.agelousis.noteitdown.ui.composableView.BorderedTextFieldValueChangedBlock
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.bold
import com.agelousis.noteitdown.utils.extensions.asIntValue

@Composable
fun RuleOfThreeView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    transformingLazyColumnState: TransformingLazyColumnState
) {
    val transformationSpec = rememberTransformationSpec()
    TransformingLazyColumn(
        modifier = modifier,
        state = transformingLazyColumnState,
        contentPadding = contentPadding
    ) {
        item {
            ListHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .transformedHeight(
                        scope = this,
                        transformationSpec = transformationSpec
                    ),
                transformation = SurfaceTransformation(
                    spec = transformationSpec
                )
            ) {
                Text(
                    text = stringResource(
                        id = R.string.key_rule_of_three_label
                    )
                )
            }
        }
        item {
            RuleOfThreeProcessView()
        }
    }
}

@Composable
private fun RuleOfThreeProcessView() {
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
                borderedTextFieldValueChangedBlock = newValue@ {
                    startingPointValue = this@newValue
                }
            )
            // Starting point target value
            PointChipValue(
                rowScope = this,
                label = "y =",
                value = startingPointTargetValue,
                borderedTextFieldValueChangedBlock = newValue@ {
                    startingPointTargetValue = this@newValue
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
                borderedTextFieldValueChangedBlock = newValue@ {
                    endingPointValue = this@newValue
                }
            )
            // Ending point target value
            ResultChipValue(
                rowScope = this,
                result = getRuleOfThreeResult(
                    startingPointValue = startingPointValue.toFloatOrNull(),
                    startingPointTargetValue = startingPointTargetValue.toFloatOrNull(),
                    endingPointValue = endingPointValue.toFloatOrNull()
                )
            )
        }
        Text(
            text = "(z * y) / x = ?",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun PointChipValue(
    rowScope: RowScope,
    label: String,
    value: String,
    borderedTextFieldValueChangedBlock: BorderedTextFieldValueChangedBlock
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
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
            BorderedTextField(
                modifier = Modifier
                    .height(
                        height = 50.dp
                    ),
                value = value,
                borderedTextFieldValueChangedBlock = borderedTextFieldValueChangedBlock
            )
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
                style = MaterialTheme.typography.labelMedium
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
                        style = MaterialTheme.typography.labelMedium.bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                ),
                border = ChipDefaults.chipBorder(
                    borderStroke = BorderStroke(
                        width = 1.dp,
                        color = Color.White
                    )
                ),
                onClick = {}
            )
        }
    }
}

private fun getRuleOfThreeResult(
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

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun RuleOfThreeLayoutPreview() {
    NoteItDownTheme {
        RuleOfThreeView(
            contentPadding = PaddingValues(
                all = 24.dp
            ),
            transformingLazyColumnState = rememberTransformingLazyColumnState()
        )
    }
}