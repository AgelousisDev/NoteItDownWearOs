package com.agelousis.noteitdown.models

import com.agelousis.noteitdown.models.enumerations.ProductQuantityType

data class ProductDataModel(
    val productLabel: String? = null,
    var productQuantity: Double = 0.0,
    var productQuantityType: ProductQuantityType = ProductQuantityType.GRAM
) {

    companion object {
        val empty
            get() = ProductDataModel()
    }
}