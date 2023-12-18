package com.agelousis.noteitdown.models

import com.agelousis.noteitdown.models.enumerations.ProductQuantityType

data class ProductDataModel(
    var id: Int? = null,
    var productImageUrl: String? = null,
    var productLabel: String? = null,
    var productQuantity: Double = 0.0,
    var productQuantityType: ProductQuantityType = ProductQuantityType.GRAM
) {

    companion object {
        val empty
            get() = ProductDataModel()
    }

    val isEmpty
        get() = productLabel.isNullOrEmpty()
                && productQuantity == 0.0

    infix fun withId(
        id: Int
    ) = this.also { productDataModel ->
        productDataModel.id = id
    }

    fun update(
        productLabel: String?,
        productQuantity: Double,
        productQuantityType: ProductQuantityType
    ) {
        this.productLabel = productLabel
        this.productQuantity = productQuantity
        this.productQuantityType = productQuantityType
    }
}