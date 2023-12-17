package com.agelousis.noteitdown.noteItDown.ui.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.medium
import com.agelousis.noteitdown.ui.theme.withTextAlign
import com.agelousis.noteitdown.utils.extensions.CompletionBlock

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
            value = productDataModel.productQuantity.takeIf {
                it > 0.0
            }?.toString() ?: ""
        )
    }
    Chip(
        modifier = modifier,
        onClick = {},
        label = {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = productLabel
                    ?: "",
                onValueChange = { value ->
                    onProductLabel(value)
                },
                textStyle = MaterialTheme.typography.body1.medium
                        withTextAlign TextAlign.Center,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                decorationBox = { innerTextField ->
                    if (productLabel.isNullOrEmpty())
                        Text(
                            text = stringResource(id = R.string.key_product_name_here_label),
                            style = MaterialTheme.typography.caption3
                                    withTextAlign TextAlign.Center
                        )
                    innerTextField()
                }
            )
        },
        secondaryLabel = {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = productQuantity,
                onValueChange = { value ->
                    onProductQuantity(
                        value
                    )
                },
                enabled = !productLabel.isNullOrEmpty(),
                textStyle = MaterialTheme.typography.caption2
                        withTextAlign TextAlign.Center,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if ((productQuantity.replace(
                                oldValue = productDataModel.productQuantityType.code,
                                newValue = ""
                            ).toDoubleOrNull() ?: 0.0) > 0.0
                        )
                            saveBlock(
                                ProductDataModel(
                                    id = productDataModel.id,
                                    productLabel = productLabel,
                                    productQuantity = productQuantity.replace(
                                        oldValue = productDataModel.productQuantityType.code,
                                        newValue = ""
                                    ).toDoubleOrNull() ?: 0.0
                                )
                            )
                    }
                ),
                decorationBox = { innerTextField ->
                    Text(
                        modifier = Modifier
                            .padding(
                                top = 16.dp
                            )
                            .fillMaxWidth(),
                        text = productDataModel.productQuantityType.code,
                        style = MaterialTheme.typography.caption3
                                withTextAlign TextAlign.Center
                    )
                    innerTextField()
                }
            )
        },
        icon = {
            Image(
                modifier = Modifier
                    .size(
                        size = 28.dp
                    ),
                painter = painterResource(id = R.drawable.ic_food_drink),
                contentDescription = null
            )
        },
        shape = RoundedCornerShape(
            size = 16.dp
        )
    )
}

@Preview
@Composable
fun ProductViewPreview() {
    NoteItDownTheme {
        ProductView(
            modifier = Modifier
                .width(
                    width = 200.dp
                ),
            productDataModel = ProductDataModel(
                id = 0,
                productLabel = "Banana",
                productQuantity = 100.0,
                productQuantityType = ProductQuantityType.GRAM
            ),
            saveBlock = {}
        )
    }
}