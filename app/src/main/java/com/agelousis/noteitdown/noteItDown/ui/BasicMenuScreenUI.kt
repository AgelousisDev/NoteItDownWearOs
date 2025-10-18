package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.Card
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
import com.agelousis.noteitdown.ui.theme.medium

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
    val density = LocalDensity.current
    val screenWidth = LocalWindowInfo.current.containerSize.width
    val resources = LocalResources.current
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
                        id = R.string.app_name
                    )
                )
            }
        }
        items(
            items = NoteItDownManagementChip.entries
        ) { noteItDownManagementChip ->
            Card(
                modifier = Modifier
                    .size(
                        width = with(
                            receiver = density
                        ) {
                            screenWidth.toDp() - 96.dp
                        },
                        height = 40.dp
                    )
                    .transformedHeight(
                        scope = this,
                        transformationSpec = transformationSpec
                    ),
                onClick = {
                    noteItDownManagementChip.action(
                        addNoteBlock = addNoteBlock,
                        notesListBlock = notesListBlock,
                        methodOfThreeBlock = methodOfThreeBlock,
                        productsWithQuantityBlock = productsWithQuantityBlock
                    )
                },
                transformation = SurfaceTransformation(
                    spec = transformationSpec
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = noteItDownManagementChip.icon,
                        contentDescription = noteItDownManagementChip.name,
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                    Text(
                        text = noteItDownManagementChip label resources,
                        style = MaterialTheme.typography.labelMedium.medium
                    )
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