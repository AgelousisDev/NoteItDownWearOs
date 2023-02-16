package com.agelousis.noteitdown.tiles.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.glance.wear.tiles.action.actionRunCallback
import androidx.glance.wear.tiles.curved.*
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.tiles.actionCallback.NoteItDownTileActionCallback
import com.agelousis.noteitdown.utils.extensions.toModelList
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.ui.theme.Purple500
import com.agelousis.noteitdown.ui.theme.Purple700

private val noteItDownAppWidgetGlanceModifier =
    GlanceModifier
        .clickable(
            onClick = actionRunCallback<NoteItDownTileActionCallback>()
        )

@Composable
fun NoteItDownTileLayout() {
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
        NotesListTileLayout(
            modifier = GlanceModifier
                .padding(
                    top = 24.dp
                ),
            noteDataModelList = noteDataModelList
        )
    }
}

@Composable
private fun NotesListTileLayout(
    modifier: GlanceModifier,
    noteDataModelList: List<NoteDataModel>
) {
    val context = LocalContext.current
    GlanceTopTile(
        modifier = modifier,
        title = context.resources.getString(R.string.app_name),
        subtitle = context.resources.getString(R.string.key_notes_label)
    )
    GlanceVerticalSpacer(
        height = 8.dp
    )
    for (noteDataModel in noteDataModelList.take(
        n = 2
    )) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlanceCircleBox(
                value = 500,
                text = noteDataModel.tag?.take(
                    n = 5
                ) ?: "",
                target = 500
            )
            GlanceHorizontalSpacer(
                width = 4.dp
            )
            Text(
                text = noteDataModel.note
                    ?: "",
                style = TextStyle(
                    color = ColorProvider(
                        color = Color(
                            color = 0xFF949aa1
                        )
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = GlanceModifier
                    .width(
                        width = 100.dp
                    )
            )
        }
        GlanceVerticalSpacer(
            height = 4.dp
        )
    }
}

@Composable
private fun GlanceCircleBox(value: Int, text: String, target: Int) {
    val context = LocalContext.current
    Box(
        modifier = GlanceModifier
            .size(
                size = 58.dp
            ),
        contentAlignment = Alignment.Center
    ) {

        val sweepProgress = (value.toFloat() / target.toFloat()) * 360f

        CurvedRow {
            curvedLine(
                color = ColorProvider(
                    color = Purple500
                ),
                curvedModifier = GlanceCurvedModifier
                    .sweepAngleDegrees(
                        degrees = 360f
                    )
                    .thickness(
                        thickness = 3.dp
                    )
            )
        }

        CurvedRow(
            anchorDegrees = 270f,
            anchorType = AnchorType.Start
        ) {
            curvedLine(
                color = ColorProvider(
                    color = Purple700
                ),
                curvedModifier = GlanceCurvedModifier
                    .sweepAngleDegrees(
                        degrees = sweepProgress
                    )
                    .thickness(
                        thickness = 3.dp
                    )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = context.resources.getString(R.string.key_tag_label),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = text,
                style = TextStyle(
                    fontSize = 8.sp,
                    color = ColorProvider(
                        color = Color(
                            color = 0xFF949aa1
                        )
                    )
                )
            )
        }
    }
}

@Composable
private fun GlanceVerticalSpacer(height: Dp) {
    Spacer(
        modifier = GlanceModifier.height(height = height)
    )
}

@Composable
private fun GlanceHorizontalSpacer(width: Dp) {
    Spacer(
        modifier = GlanceModifier.width(width = width)
    )
}

@Composable
private fun GlanceTopTile(
    modifier: GlanceModifier = GlanceModifier,
    title: String,
    subtitle: String
) {
    Text(
        text = title,
        style = TextStyle(
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier
    )
    Text(
        text = subtitle,
        style = TextStyle(
            color = ColorProvider(
                color = Color(
                    color = 0xFF949aa1
                )
            )
        )
    )
}

@Preview
@Composable
private fun NoteItDownTileLayoutPreview() {
    NoteItDownTileLayout()
}
