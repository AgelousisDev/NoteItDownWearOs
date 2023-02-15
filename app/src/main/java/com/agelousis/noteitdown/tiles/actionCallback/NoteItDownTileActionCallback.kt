package com.agelousis.noteitdown.tiles.actionCallback

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.wear.tiles.action.ActionCallback
import com.agelousis.noteitdown.noteItDown.NoteItDownActivity

class NoteItDownTileActionCallback: ActionCallback {

    override suspend fun onAction(context: Context, glanceId: GlanceId) {
        context.startActivity(
            Intent(
                context,
                NoteItDownActivity::class.java
            ).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

}