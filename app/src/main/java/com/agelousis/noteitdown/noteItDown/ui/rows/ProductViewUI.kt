package com.agelousis.noteitdown.noteItDown.ui.rows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.rounded.FoodBank
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.medium
import com.agelousis.noteitdown.ui.theme.withTextAlign
import com.agelousis.noteitdown.utils.extensions.CompletionBlock
import com.agelousis.noteitdown.utils.extensions.decimalPlaces

@Composable
fun ProductView(
    modifier: Modifier = Modifier,
    productDataModel: ProductDataModel,
    saveBlock: CompletionBlock<ProductDataModel>
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (productLabel, onProductLabel) = remember {
        mutableStateOf(value = productDataModel.productLabel)
    }
    val (productQuantity, onProductQuantity) = remember {
        mutableStateOf(
            value = "%.${productDataModel.productQuantity?.decimalPlaces ?: 0}f%s".format(
                productDataModel.productQuantity,
                productDataModel.productQuantityType.code
            )
        )
    }
    val (doneState, onDoneState) = remember {
        mutableStateOf(value = false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp
        )
    ) {
        Chip(
            modifier = modifier,
            onClick = {},
            label = {
                BasicTextField(
                    value = productLabel
                        ?: "",
                    onValueChange = { value ->
                        if (!doneState)
                            onProductLabel(value)
                        onDoneState(false)
                    },
                    textStyle = MaterialTheme.typography.body1.medium
                            withTextAlign TextAlign.Center,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onDoneState(true)
                            keyboardController?.hide()
                        }
                    )
                )
            },
            secondaryLabel = {
                BasicTextField(
                    value = productQuantity,
                    onValueChange = { value ->
                        if (!doneState)
                            onProductQuantity(
                                "%.${value.toDouble().decimalPlaces}f%s".format(
                                    value.toDouble(),
                                    productDataModel.productQuantityType?.code
                                )
                            )
                        onDoneState(false)
                    },
                    textStyle = MaterialTheme.typography.caption2
                            withTextAlign TextAlign.Center,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onDoneState(true)
                            keyboardController?.hide()
                        }
                    )
                )
            },
            icon = {
                Icon(
                    imageVector = productDataModel.productIcon,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(
                size = 16.dp
            )
        )
        CompactButton(
            onClick = {
                saveBlock(
                    ProductDataModel(
                        productLabel = productLabel,
                        productQuantity = productQuantity.replace(
                            oldValue = productDataModel.productQuantityType.code,
                            newValue = ""
                        ).toDoubleOrNull()
                    )
                )
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun ProductViewPreview() {
    NoteItDownTheme {
        ProductView(
            productDataModel = ProductDataModel(
                productLabel = "Banana",
                productIcon = Icons.Rounded.FoodBank,
                productQuantity = 100.0,
                productQuantityType = ProductQuantityType.GRAM
            ),
            saveBlock = {}
        )
    }
}