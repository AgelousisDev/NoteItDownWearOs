package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.ui.theme.Typography
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun NoteItDownActivityLayout() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStorageHelper = PreferencesStoreHelper(
        context = context
    )
    Box(
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp
            )
        ) {
            var note by remember {
                mutableStateOf<String?>(value = null)
            }
            Text(
                text = stringResource(
                    id = R.string.app_name
                ),
                style = Typography.body1
            )
            OutlinedTextField(
                value = note
                    ?: "",
                onValueChange = {
                    note = it
                },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.key_add_note_label
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
            )
            IconButton(
                enabled = !note.isNullOrEmpty(),
                onClick = {
                    coroutineScope.launch {
                        preferencesStorageHelper addNote NoteDataModel(
                            tag = "1",
                            note = note
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.Green
                )
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    NoteItDownActivityLayout()
}