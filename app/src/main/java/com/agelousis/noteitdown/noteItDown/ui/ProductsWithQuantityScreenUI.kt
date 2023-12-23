package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberRevealState
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.SwipeToRevealChip
import androidx.wear.compose.material.SwipeToRevealDefaults
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.noteItDown.ui.rows.ProductView
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel
import com.agelousis.noteitdown.ui.composableView.AnimatedLayout
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearFoundationApi::class, ExperimentalWearMaterialApi::class)
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
                if (productDataModelList.isNullOrEmpty()
                    || this@ProductDataModelList.none(predicate = ProductDataModel::isEmpty)
                )
                    this@ProductDataModelList.add(
                        element = ProductDataModel.empty
                    )
            }
        }
    }
    AnimatedLayout(
        initialState = isOnPreview,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = scalingLazyColumnState,
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            autoCentering = AutoCenteringParams(
                itemIndex = 1
            ),
            contentPadding = PaddingValues(
                vertical = 16.dp,
            )
        ) {
            items(
                items = if (isOnPreview) (productDataModelListForPreview
                    ?: listOf()) else productDataModelStateList
            ) { productDataModel ->
                key(
                    productDataModelStateList
                ) {
                    val revealState = rememberRevealState()
                    SwipeToRevealChip(
                        revealState = revealState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(
                                align = Alignment.CenterHorizontally
                            )
                            // Use edgeSwipeToDismiss to allow SwipeToDismissBox to capture swipe events
                            .semantics {
                                // Use custom actions to make the primary and secondary actions accessible
                                customActions = listOf(
                                    CustomAccessibilityAction(
                                        label = context.resources.getString(R.string.key_delete_label),
                                        action = { true }
                                    )
                                )
                            },
                        primaryAction = {
                            SwipeToRevealPrimaryAction(
                                revealState = revealState,
                                icon = {
                                    Icon(
                                        imageVector = SwipeToRevealDefaults.Delete,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.key_delete_label)
                                    )
                                },
                                onClick = {
                                    coroutineScope.launch {
                                        removeProductData(
                                            preferencesStoreHelper = preferencesStoreHelper,
                                            productDataModel = productDataModel
                                        )
                                    }
                                }
                            )
                        },
                        onFullSwipe = {
                            coroutineScope.launch {
                                removeProductData(
                                    preferencesStoreHelper = preferencesStoreHelper,
                                    productDataModel = productDataModel
                                )
                            }
                        }
                    ) {
                        ProductView(
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