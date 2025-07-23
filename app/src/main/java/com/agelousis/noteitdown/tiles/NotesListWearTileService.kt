package com.agelousis.noteitdown.tiles

import androidx.compose.runtime.Composable
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.wear.tiles.GlanceTileService
import com.agelousis.noteitdown.tiles.helper.CustomGlanceStateDefinition
import com.agelousis.noteitdown.tiles.ui.NotesListTileLayout

class NotesListWearTileService: GlanceTileService() {

    /*companion object {
        private const val APP_START_ACTION_PARAM = "appStartActionParam"
        val appStartActionParam = ActionParameters.Key<Boolean>(name = APP_START_ACTION_PARAM)
    }*/

    override var stateDefinition: GlanceStateDefinition<*> = CustomGlanceStateDefinition

    @Composable
    override fun Content() {
        NotesListTileLayout()
    }

}