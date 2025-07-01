package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.noteItDown.enumerations.NoteItDownManagementChip
import com.agelousis.noteitdown.ui.extensions.ButtonBlock
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.bold
import com.agelousis.noteitdown.ui.theme.withSize

@Composable
fun BasicMenuScreenView(
    scalingLazyColumnState: ScalingLazyListState,
    addNoteBlock: ButtonBlock,
    notesListBlock: ButtonBlock,
    methodOfThreeBlock: ButtonBlock,
    productsWithQuantityBlock: ButtonBlock
) {
    val density = LocalDensity.current
    val screenWidth = LocalWindowInfo.current.containerSize.width
    val resources = LocalResources.current
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        state = scalingLazyColumnState
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(
                        bottom = 16.dp
                    ),
                text = stringResource(
                    id = R.string.app_name
                ),
                style = MaterialTheme.typography.bodyLarge.bold
                    withSize 20.sp
            )
        }
        items(
            items = NoteItDownManagementChip.entries
        ) { noteItDownManagementChip ->
            Chip(
                modifier = Modifier
                    .width(
                        width = with(
                            receiver = density
                        ) {
                            (screenWidth / 1.5).toInt().toDp()
                        }
                    ),
                onClick = {
                    noteItDownManagementChip.action(
                        addNoteBlock = addNoteBlock,
                        notesListBlock = notesListBlock,
                        methodOfThreeBlock = methodOfThreeBlock,
                        productsWithQuantityBlock = productsWithQuantityBlock
                    )
                },
                label = {
                    Text(
                        text = noteItDownManagementChip label resources,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                icon = {
                    Icon(
                        imageVector = noteItDownManagementChip.icon,
                        contentDescription = noteItDownManagementChip.name,
                        tint = noteItDownManagementChip.tint
                    )
                },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.White.copy(
                        alpha = .2f
                    )
                )
            )
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun BasicMenuScreenViewPreview() {
    NoteItDownTheme {
        BasicMenuScreenView(
            scalingLazyColumnState = rememberScalingLazyListState(),
            addNoteBlock = {},
            notesListBlock = {},
            methodOfThreeBlock = {},
            productsWithQuantityBlock = {}
        )
    }
}