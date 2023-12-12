package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.FoodBank
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.noteItDown.ui.rows.ProductView
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun ProductsWithQuantityScreenLayout(
    scalingLazyColumnState: ScalingLazyListState,
    productDataModelListForPreview: List<ProductDataModel>? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStoreHelper = remember {
        PreferencesStoreHelper(
            context = context
        )
    }
    val productDataModelStateList = preferencesStoreHelper.productDataModelList.collectAsState(
        initial = productDataModelListForPreview ?: listOf()
    ).value?.toMutableStateList()
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        state = scalingLazyColumnState,
    ) {
        items(
            items = productDataModelStateList ?: listOf(),
            key = { productDataModel ->
                productDataModel.productLabel
                    ?: ""
            }
        ) { productDataModel ->
            ProductView(
                productDataModel = productDataModel,
                saveBlock = ProductDataModel@ {
                    coroutineScope.launch {
                        saveProductData(
                            preferencesStoreHelper = preferencesStoreHelper,
                            productDataModel = productDataModel
                        )
                    }
                }
            )
        }
        item(
            key = "Add"
        ) {
            CompactButton(
                onClick = {
                    productDataModelStateList?.add(
                        ProductDataModel.empty
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )
            }
        }
    }
}

private suspend fun saveProductData(
    preferencesStoreHelper: PreferencesStoreHelper,
    productDataModel: ProductDataModel
) {
    preferencesStoreHelper saveProductData productDataModel
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun ProductsWithQuantityScreenLayoutPreview() {
    NoteItDownTheme {
        ProductsWithQuantityScreenLayout(
            scalingLazyColumnState = rememberScalingLazyListState(),
            productDataModelListForPreview = listOf(
                ProductDataModel(
                    productLabel = "Banana",
                    productIcon = Icons.Rounded.FoodBank,
                    productQuantity = 100.0,
                    productQuantityType = ProductQuantityType.GRAM
                ),
                ProductDataModel(
                    productLabel = "Carrot",
                    productIcon = Icons.Rounded.FoodBank,
                    productQuantity = 76.5,
                    productQuantityType = ProductQuantityType.GRAM
                )
            )
        )
    }
}