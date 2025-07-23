package com.agelousis.noteitdown.tiles

import androidx.compose.runtime.Composable
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.wear.tiles.GlanceTileService
import com.agelousis.noteitdown.tiles.helper.CustomGlanceStateDefinition
import com.agelousis.noteitdown.tiles.ui.RuleOfThreeTileLayout

class RuleOfThreeWearTileService: GlanceTileService() {

    override var stateDefinition: GlanceStateDefinition<*> = CustomGlanceStateDefinition

    @Composable
    override fun Content() {
        RuleOfThreeTileLayout()
    }

}