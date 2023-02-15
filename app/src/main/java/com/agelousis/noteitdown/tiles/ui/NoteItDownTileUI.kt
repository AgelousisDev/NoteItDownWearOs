package com.agelousis.noteitdown.tiles.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.wear.tiles.action.actionRunCallback
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.tiles.actionCallback.NoteItDownTileActionCallback
import com.agelousis.noteitdown.utils.extensions.toModelList
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import com.agelousis.noteitdown.R

private val noteItDownAppWidgetGlanceModifier =
    GlanceModifier
        .clickable(
            onClick = actionRunCallback<NoteItDownTileActionCallback>()
        )

@Composable
fun NoteItDownTileLayout() {
    val context = LocalContext.current
    val preferences = currentState<Preferences>()
    val noteDataModelList = preferences[
            PreferencesStoreHelper.NOTE_DATA_DATA_KEY
    ]?.takeIf {
        it.isNotEmpty()
    }?.toModelList<NoteDataModel>() ?: listOf()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = noteItDownAppWidgetGlanceModifier
            .fillMaxSize()
    ) {
        Text(
            text = context.resources.getString(R.string.app_name),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = GlanceModifier
                .padding(
                    top = 16.dp
                )
        )
        if (noteDataModelList.isEmpty())
            Text(
                text = context.resources.getString(
                    R.string.key_empty_notes_label
                ),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = GlanceModifier
                    .padding(
                        top = 32.dp
                    )
            )
        for ((index, noteDataModel) in noteDataModelList.withIndex())
            Row(
                modifier = GlanceModifier
                    .padding(
                        top =  if (index == 0) 16.dp else 8.dp
                    )
            ) {
                Text(
                    text = noteDataModel.tag?.let {
                        "$it → "
                    } ?: "",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = noteItDownAppWidgetGlanceModifier
                )
                Text(
                    text = noteDataModel.note
                        ?: "",
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = noteItDownAppWidgetGlanceModifier
                )
            }
    }
}

@Preview
@Composable
fun NoteItDownTileLayoutPreview() {
    NoteItDownTileLayout()
}
