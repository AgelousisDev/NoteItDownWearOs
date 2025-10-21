package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.RevealState
import androidx.wear.compose.material3.SwipeToReveal
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.rememberRevealState
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.network.SuccessBlock
import com.agelousis.noteitdown.noteItDown.ui.views.ProductView
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel
import com.agelousis.noteitdown.ui.composableView.SwipeToRevealActionView
import com.agelousis.noteitdown.ui.enumerations.SwipeToRevealAction
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@Composable
fun ProductsWithQuantityScreenView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    transformingLazyColumnState: TransformingLazyColumnState,
    viewModel: NoteItDownBaseViewModel,
    productDataModelListForPreview: List<ProductDataModel>? = null,
    productImagePreviewBlock: SuccessBlock<String>
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
    val transformationSpec = rememberTransformationSpec()
    LaunchedEffect(
        key1 = Unit
    ) {
        transformingLazyColumnState.animateScrollToItem(
            index = 0
        )
    }
    //val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    TransformingLazyColumn(
        modifier = modifier,
        state = transformingLazyColumnState,
        contentPadding = contentPadding
    ) {
        items(
            items = if (isOnPreview)
                (productDataModelListForPreview ?: listOf())
            else
                productDataModelStateList,
            key = { productDataModel ->
                productDataModel.productLabel
                    ?: ""
            }
        ) { productDataModel ->
            val swipeToRevealState = rememberRevealState()
            SwipeToReveal(
                revealState = swipeToRevealState,
                primaryAction = {
                    ProductViewDeleteAction(
                        swipeToRevealState = swipeToRevealState,
                        preferencesStoreHelper = preferencesStoreHelper,
                        productDataModel = productDataModel
                    )
                },
                onSwipePrimaryAction = {
                    coroutineScope.launch {
                        removeProductData(
                            preferencesStoreHelper = preferencesStoreHelper,
                            productDataModel = productDataModel
                        )
                    }
                }
            ) {
                ProductView(
                    transformingLazyColumnItemScope = this@items,
                    transformationSpec = transformationSpec,
                    viewModel = viewModel,
                    productDataModel = productDataModel,
                    productImagePreviewBlock = productImagePreviewBlock,
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
        //region Add product item
        item {
            ProductView(
                transformingLazyColumnItemScope = this,
                transformationSpec = transformationSpec,
                viewModel = viewModel,
                productDataModel = ProductDataModel.empty,
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
        //endregion
    }
}

@Composable
private fun ProductViewDeleteAction(
    swipeToRevealState: RevealState,
    preferencesStoreHelper: PreferencesStoreHelper,
    productDataModel: ProductDataModel
) {
    val coroutineScope = rememberCoroutineScope()
    SwipeToRevealActionView(
        revealState = swipeToRevealState,
        swipeToRevealAction = SwipeToRevealAction.DELETE,
        swipeToRevealActionBlock = {
            coroutineScope.launch {
                removeProductData(
                    preferencesStoreHelper = preferencesStoreHelper,
                    productDataModel = productDataModel
                )
            }
        }
    )
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
fun ProductsWithQuantityScreenViewPreview() {
    NoteItDownTheme {
        ProductsWithQuantityScreenView(
            contentPadding = PaddingValues(
                all = 24.dp
            ),
            transformingLazyColumnState = rememberTransformingLazyColumnState(),
            viewModel = viewModel(),
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
                ProductDataModel(
                    id = 2,
                    productLabel = "Avocado",
                    productQuantity = 120.0,
                    productQuantityType = ProductQuantityType.GRAM
                ),
                ProductDataModel(
                    id = 3,
                    productLabel = "Bread",
                    productQuantity = 40.0,
                    productQuantityType = ProductQuantityType.GRAM
                ),
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
                ProductDataModel(
                    id = 2,
                    productLabel = "Avocado",
                    productQuantity = 120.0,
                    productQuantityType = ProductQuantityType.GRAM
                ),
                ProductDataModel(
                    id = 3,
                    productLabel = "Bread",
                    productQuantity = 40.0,
                    productQuantityType = ProductQuantityType.GRAM
                ),
                ProductDataModel.empty
            ),
            productImagePreviewBlock = {}
        )
    }
}