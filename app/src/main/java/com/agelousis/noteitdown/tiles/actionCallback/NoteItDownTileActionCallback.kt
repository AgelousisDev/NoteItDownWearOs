package com.agelousis.noteitdown.tiles.actionCallback

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.wear.tiles.action.ActionCallback
import com.agelousis.noteitdown.noteItDown.NoteItDownActivity
import com.agelousis.noteitdown.noteItDown.navigation.NoteItDownNavigationScreen

class NoteItDownTileActionCallback: ActionCallback {

    override suspend fun onAction(context: Context, glanceId: GlanceId) {
        NoteItDownActivity.show(
            context = context,
            noteItDownNavigationScreen = NoteItDownNavigationScreen.NotesListScreen,
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        )
    }

}