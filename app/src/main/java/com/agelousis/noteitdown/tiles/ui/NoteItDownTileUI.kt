package com.agelousis.noteitdown.tiles.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.*
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.tiles.NoteItDownWearTileService
import com.agelousis.noteitdown.tiles.actionCallback.NoteItDownTileActionCallback
import com.agelousis.noteitdown.utils.extensions.toModelList
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import com.agelousis.noteitdown.R

private val noteItDownAppWidgetGlanceModifier =
    GlanceModifier
        .clickable(
            onClick = actionRunCallback<NoteItDownTileActionCallback>(
                parameters = actionParametersOf(
                    NoteItDownWearTileService.appStartActionParam to true
                )
            )
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
        modifier = GlanceModifier
            .fillMaxSize()
    ) {
        if (noteDataModelList.isEmpty())
            Text(
                text = context.resources.getString(
                    R.string.key_empty_notes_label
                ),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
        for (noteDataModel in noteDataModelList)
            Text(
                text = noteDataModel.note
                    ?: "",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = noteItDownAppWidgetGlanceModifier
            )
    }
}

@Preview
@Composable
fun NoteItDownTileLayoutPreview() {
    NoteItDownTileLayout()
}
