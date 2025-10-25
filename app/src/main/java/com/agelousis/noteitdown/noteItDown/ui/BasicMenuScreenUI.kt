package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Surface
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.noteItDown.enumerations.NoteItDownManagementChip
import com.agelousis.noteitdown.ui.extensions.ButtonBlock
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BasicMenuScreenView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    transformingLazyColumnState: TransformingLazyColumnState,
    addNoteBlock: ButtonBlock,
    notesListBlock: ButtonBlock,
    methodOfThreeBlock: ButtonBlock,
    productsWithQuantityBlock: ButtonBlock
) {
    val transformationSpec = rememberTransformationSpec()
    TransformingLazyColumn(
        modifier = modifier,
        state = transformingLazyColumnState,
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        )
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
                        id = R.string.app_name
                    ),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.tertiaryDim
                            )
                        )
                    )
                )
            }
        }
        item {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                NoteItDownManagementChip.entries.forEachIndexed { index, noteItDownManagementChip ->
                    val infiniteTransition =
                        rememberInfiniteTransition(label = "InfiniteRotation")
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(
                                durationMillis = 9000,
                                easing = LinearEasing
                            ),
                            repeatMode = RepeatMode.Restart,
                            initialStartOffset = StartOffset(
                                offsetMillis = index * 1000
                            )
                        ),
                        label = "RotationAngle",
                    )
                    Surface(
                        modifier = Modifier
                            .transformedHeight(
                                scope = this@item,
                                transformationSpec = transformationSpec
                            )
                            .rotate(
                                degrees = rotation
                            ),
                        onClick = {
                            noteItDownManagementChip.action(
                                addNoteBlock = addNoteBlock,
                                notesListBlock = notesListBlock,
                                methodOfThreeBlock = methodOfThreeBlock,
                                productsWithQuantityBlock = productsWithQuantityBlock
                            )
                        },
                        shape = noteItDownManagementChip.shape.toShape(),
                        color = MaterialTheme.colorScheme.surfaceContainer
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(
                                    all = 16.dp
                                ),
                            imageVector = noteItDownManagementChip.icon,
                            contentDescription = noteItDownManagementChip.name,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun BasicMenuScreenViewPreview() {
    NoteItDownTheme {
        BasicMenuScreenView(
            contentPadding = PaddingValues(
                all = 24.dp
            ),
            transformingLazyColumnState = rememberTransformingLazyColumnState(),
            addNoteBlock = {},
            notesListBlock = {},
            methodOfThreeBlock = {},
            productsWithQuantityBlock = {}
        )
    }
}