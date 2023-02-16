package com.agelousis.noteitdown.noteItDown.ui.rows

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.ui.composableView.LinkText
import com.agelousis.noteitdown.ui.composableView.LinkTextData
import com.agelousis.noteitdown.ui.theme.Purple700
import com.agelousis.noteitdown.utils.extensions.shareText
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun NoteRowLayout(
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
                .height(
                    height = 50.dp
                ),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Note,
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            size = 24.dp
                        )
                        .wrapContentSize(
                            align = Alignment.Center
                        ),
                )
            },
            label = {
                LinkText(
                    linkTextData = arrayOf(
                        noteDataModel.tag?.let {
                            "$it\n"
                        } ?: "",
                        noteDataModel.note
                            ?: ""
                    ).mapIndexed { index, s ->
                        LinkTextData(
                            text = s,
                            textColor = if (index == 0) Color(color = 0xFF949aa1) else Color.White,
                            fontWeight = if (index == 0) FontWeight.Light else FontWeight.Medium
                        )
                    },
                    style = MaterialTheme.typography.caption3
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
                coroutineScope.launch {
                    preferencesStoreHelper setNoteAsFirst noteDataModel
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(
                    weight = 0.2f
                )
        ) {
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
                    .size(
                        size = 20.dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(
                            size = 15.dp
                        )
                )
            }
            Button(
                onClick = {
                    context shareText (noteDataModel.note ?: return@Button)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier
                    .size(
                        size = 20.dp
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(
                            size = 15.dp
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun NoteRowLayoutPreview() {
    NoteRowLayout(
        noteDataModel = NoteDataModel(
            tag = "Sample Tag",
            note = "Sample Note"
        )
    )
}