package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.ui.theme.Typography
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun NotesListScreenLayout(
    noteDataModelListForPreview: List<NoteDataModel>? = null,
    backButtonBlock: ButtonBlock
) {
    val context = LocalContext.current
    val preferencesStoreHelper = PreferencesStoreHelper(
        context = context
    )
    val noteDataModelList by preferencesStoreHelper.noteDataModelList.collectAsState(
        initial = noteDataModelListForPreview ?: listOf()
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primaryVariant,
                shape = CircleShape
            )
    ) {
        val (backButtonConstrainedReference, scalingLazyColumnConstrainedReference) =
            createRefs()
        Button(
            onClick = backButtonBlock,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .constrainAs(backButtonConstrainedReference) {
                    start.linkTo(parent.start, 24.dp)
                    top.linkTo(parent.top, 24.dp)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
        ScalingLazyColumn(
            modifier = Modifier
                .constrainAs(scalingLazyColumnConstrainedReference) {
                    start.linkTo(parent.start)
                    top.linkTo(backButtonConstrainedReference.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentPadding = PaddingValues(
                horizontal = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp
            ),
            state = rememberScalingLazyListState(),
        ) {
            if (noteDataModelList.isNullOrEmpty())
                item {
                    Icon(
                        imageVector = Icons.Filled.Image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(
                                size = 50.dp
                            )
                    )
                }
            items(
                items = noteDataModelList
                    ?: listOf()
            ) { noteDataModel ->
                NoteRowLayout(
                    noteDataModel = noteDataModel
                )
            }
        }
    }
}

@Composable
private fun NoteRowLayout(
    noteDataModel: NoteDataModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStoreHelper = PreferencesStoreHelper(
        context = context
    )
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Chip(
            modifier = Modifier
                .weight(
                    weight = 0.8f
                )
                .padding(
                    top = 10.dp
                ),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Note,
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            size = 24.dp
                        )
                        .wrapContentSize(align = Alignment.Center),
                )
            },
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onPrimary,
                    text = "${noteDataModel.tag} → ${noteDataModel.note}",
                    style = Typography.caption2
                )
            },
            onClick = {

            }
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    preferencesStoreHelper removeNote noteDataModel
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            modifier = Modifier
                .weight(
                    weight = 0.2f
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NotesListScreenLayoutPreview() {
    NotesListScreenLayout(
        noteDataModelListForPreview = listOf(
            NoteDataModel(
                tag = "Sample Tag",
                note = "Sample Note"
            )
        )
    ) {}
}