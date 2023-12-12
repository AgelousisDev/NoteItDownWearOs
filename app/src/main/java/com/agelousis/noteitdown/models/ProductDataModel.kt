package com.agelousis.noteitdown.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FoodBank
import androidx.compose.ui.graphics.vector.ImageVector
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType

data class ProductDataModel(
    val productLabel: String? = null,
    var productIcon: ImageVector = Icons.Rounded.FoodBank,
    var productQuantity: Double? = null,
    var productQuantityType: ProductQuantityType = ProductQuantityType.GRAM
) {
    companion object {
        val empty
            get() = ProductDataModel()
    }
}