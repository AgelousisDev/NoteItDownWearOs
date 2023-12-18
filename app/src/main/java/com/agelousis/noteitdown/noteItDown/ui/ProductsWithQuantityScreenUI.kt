package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberSwipeToDismissBoxState
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.noteItDown.ui.rows.ProductView
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel
import com.agelousis.noteitdown.ui.composableView.AnimatedLayout
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun ProductsWithQuantityScreenLayout(
    viewModel: NoteItDownBaseViewModel,
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
            (productDataModelList?.toMutableStateList()
                ?: mutableStateListOf()).apply ProductDataModelList@{
                if (!productDataModelList.isNullOrEmpty()
                    && this@ProductDataModelList.none(predicate = ProductDataModel::isEmpty)
                )
                    this@ProductDataModelList.add(
                        element = ProductDataModel.empty
                    )
            }
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
        horizontalAlignment = Alignment.CenterHorizontally,
        state = scalingLazyColumnState,
    ) {
        itemsIndexed(
            items = if (isOnPreview) (productDataModelListForPreview
                ?: listOf()) else productDataModelStateList
        ) { index, productDataModel ->
            val state = rememberSwipeToDismissBoxState()
            SwipeToDismissBox(
                modifier = Modifier
                    .fillMaxWidth(
                        fraction = 0.7f
                    ),
                state = state,
                onDismissed = {
                    coroutineScope.launch {
                        removeProductData(
                            preferencesStoreHelper = preferencesStoreHelper,
                            productDataModel = productDataModel
                        )
                    }
                },
                backgroundScrimColor = Color.Transparent,
                backgroundKey = productDataModel,
                contentKey = productDataModel
            ) { hasBackground ->
                if (hasBackground)
                    Box {}
                else
                    AnimatedLayout(
                        index = index,
                        initialState = isOnPreview
                    ) {
                        ProductView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            viewModel = viewModel,
                            productDataModel = productDataModel,
                            saveBlock = ProductDataModel@ {
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
            viewModel = viewModel(),
            scalingLazyColumnState = rememberScalingLazyListState(),
            productDataModelListForPreview = listOf(
                ProductDataModel(
                    id = 0,
                    productLabel = "Banana",
                    productQuantity = 100.0,
                    productQuantityType = ProductQuantityType.GRAM
                ),
                ProductDataModel(
                    id = 1,
                    productLabel = "Carrot",
                    productQuantity = 76.5,
                    productQuantityType = ProductQuantityType.GRAM
                ),
                ProductDataModel.empty
            )
        )
    }
}