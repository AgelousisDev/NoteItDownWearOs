package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.noteItDown.ui.rows.ProductView
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun ProductsWithQuantityScreenView(
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
            productDataModelList?.toMutableStateList()
                ?: mutableStateListOf()
        }
    }
    LaunchedEffect(
        key1 = productDataModelList?.size
    ) {
        if (productDataModelStateList.isEmpty()
            || productDataModelStateList.none(predicate = ProductDataModel::isEmpty)
        ) {
            delay(
                duration = 1.seconds
            )
            productDataModelStateList.add(
                element = ProductDataModel.empty
            )
        }
    }
    //val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            ),
        state = scalingLazyColumnState,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            vertical = 16.dp,
        ),
        anchorType = ScalingLazyListAnchorType.ItemStart
    ) {
        items(
            items = if (isOnPreview) (productDataModelListForPreview
                ?: listOf()) else productDataModelStateList,
            key = { productDataModel ->
                productDataModel.id
                    ?: 0
            }
        ) { productDataModel ->
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
                },
                deleteBlock =
                    if (!productDataModel.isEmpty) {
                        ProductDataModel@ {
                            coroutineScope.launch {
                                removeProductData(
                                    preferencesStoreHelper = preferencesStoreHelper,
                                    productDataModel = this@ProductDataModel
                                )
                            }
                        }
                    }
                    else
                        null
            )
            /*val revealState = rememberRevealState()
            BasicSwipeToDismissBox(
                state = swipeToDismissBoxState,
                userSwipeEnabled = false,
                contentKey = productDataModel
            ) {
                SwipeToRevealChip(
                    //revealState = revealState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(
                            align = Alignment.CenterHorizontally
                        )
                        // Use edgeSwipeToDismiss to allow SwipeToDismissBox to capture swipe events
                        .edgeSwipeToDismiss(
                            swipeToDismissBoxState = swipeToDismissBoxState
                        )
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

                }
            }*/
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
fun ProductsWithQuantityScreenViewPreview() {
    NoteItDownTheme {
        ProductsWithQuantityScreenView(
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
            )
        )
    }
}