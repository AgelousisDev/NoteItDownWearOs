package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TextButton
import androidx.wear.compose.material3.TextButtonDefaults
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.noteItDown.enumerations.NoteItDownManagementChip
import com.agelousis.noteitdown.ui.extensions.ButtonBlock
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme

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
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
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
                shapes = TextButtonDefaults.animatedShapes(),
                colors = TextButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(
                        alpha = .3f
                    )
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = noteItDownManagementChip.icon,
                        contentDescription = noteItDownManagementChip.name,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = noteItDownManagementChip label resources,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primaryDim
                        )
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