package com.agelousis.noteitdown.tiles.actionCallback

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.agelousis.noteitdown.noteItDown.NoteItDownActivity
import com.agelousis.noteitdown.tiles.NoteItDownWearTileService

class NoteItDownTileActionCallback : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        if (
            parameters.getOrDefault(
                key = NoteItDownWearTileService.appStartActionParam,
                defaultValue = false
            )
        )
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