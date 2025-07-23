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
import com.agelousis.noteitdown.tiles.actionCallback.ProductsWithQuantityTileActionCallback

private val productsWithQuantityAppWidgetGlanceModifier =
    GlanceModifier
        .clickable(
            onClick = actionRunCallback<ProductsWithQuantityTileActionCallback>()
        )

@Composable
fun ProductsWithQuantityTileLayout() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically,
        modifier = productsWithQuantityAppWidgetGlanceModifier
            .fillMaxSize()

    ) {
        Text(
            text = context.resources.getString(R.string.key_products_with_quantity_label),
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
        Image(
            provider = ImageProvider(
                resId = R.drawable.ic_food_drink
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
    }
}