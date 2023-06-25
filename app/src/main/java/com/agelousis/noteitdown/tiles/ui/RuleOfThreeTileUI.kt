package com.agelousis.noteitdown.tiles.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.wear.tiles.action.actionRunCallback
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.tiles.actionCallback.RuleOfThreeTileActionCallback

private val ruleOfThreeAppWidgetGlanceModifier =
    GlanceModifier
        .clickable(
            onClick = actionRunCallback<RuleOfThreeTileActionCallback>()
        )

@Composable
fun RuleOfThreeTileLayout() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically,
        modifier = ruleOfThreeAppWidgetGlanceModifier
            .fillMaxSize()

    ) {
        Text(
            text = context.resources.getString(R.string.key_rule_of_three_label),
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
        Image(
            provider = ImageProvider(
                resId = R.drawable.ic_math
            ),
            contentDescription = "",
            modifier = GlanceModifier
                .padding(
                    top = 8.dp
                )
                .size(
                    size = 80.dp
                )
        )
        Text(
            text = "(z * y) / x = ?",
            modifier = GlanceModifier
                .padding(
                    top = 8.dp
                )
        )
    }
}