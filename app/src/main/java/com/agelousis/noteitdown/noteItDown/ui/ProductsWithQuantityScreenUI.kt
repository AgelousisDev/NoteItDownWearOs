package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberSwipeToDismissBoxState
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.noteItDown.ui.rows.ProductView
import com.agelousis.noteitdown.ui.composableView.AnimatedLayout
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun ProductsWithQuantityScreenLayout(
    scalingLazyColumnState: ScalingLazyListState,
    productDataModelListForPreview: List<ProductDataModel>? = null
) {
    val context = LocalContext.current
    val isOnPreview = LocalInspectionMode.current
    val coroutineScope = rememberCoroutineScope()
    val preferencesStoreHelper = remember {
        PreferencesStoreHelper(
            context = context
        )
    }
    val productDataModelList by preferencesStoreHelper.productDataModelList.collectAsState(
        initial = productDataModelListForPreview ?: listOf()
    )
    val productDataModelStateList by remember {
        derivedStateOf {
            productDataModelList?.toMutableStateList()
                ?: mutableStateListOf()
        }
    }
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
        itemsIndexed(
            items = if (isOnPreview) (productDataModelListForPreview
                ?: listOf()) else productDataModelStateList
        ) { index, productDataModel ->
            val dismissState = rememberSwipeToDismissBoxState()
            SwipeToDismissBox(
                state = dismissState,
                backgroundScrimColor = Color.Transparent,
                backgroundKey = productDataModel,
                contentKey = productDataModel,
                onDismissed = {
                    coroutineScope.launch {
                        removeProductData(
                            preferencesStoreHelper = preferencesStoreHelper,
                            productDataModel = productDataModel
                        )
                    }
                }
            ) { isBackground ->
                if (isBackground)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colors.secondaryVariant
                            )
                    )
                else
                    AnimatedLayout(
                        index = index
                    ) {
                        ProductView(
                            productDataModel = productDataModel,
                            saveBlock = ProductDataModel@{
                                coroutineScope.launch {
                                    saveProductData(
                                        preferencesStoreHelper = preferencesStoreHelper,
                                        productDataModel = this@ProductDataModel
                                    )
                                }
                            }
                        )
                    }
            }
        }
        item(
            key = "Add"
        ) {
            CompactButton(
                onClick = {
                    productDataModelStateList.add(
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

private suspend fun removeProductData(
    preferencesStoreHelper: PreferencesStoreHelper,
    productDataModel: ProductDataModel
) {
    preferencesStoreHelper removeProductData productDataModel
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
                    productQuantity = 100.0,
                    productQuantityType = ProductQuantityType.GRAM
                ),
                ProductDataModel(
                    productLabel = "Carrot",
                    productQuantity = 76.5,
                    productQuantityType = ProductQuantityType.GRAM
                )
            )
        )
    }
}