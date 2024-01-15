package com.agelousis.noteitdown.noteItDown.ui.rows

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material3.MaterialTheme
import coil.compose.AsyncImage
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.models.enumerations.ProductQuantityType
import com.agelousis.noteitdown.network.SuccessBlock
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme
import com.agelousis.noteitdown.ui.theme.medium
import com.agelousis.noteitdown.ui.theme.withColor
import com.agelousis.noteitdown.ui.theme.withTextAlign
import com.agelousis.noteitdown.utils.extensions.CompletionBlock
import com.agelousis.noteitdown.utils.extensions.imageRequest

@Composable
fun ProductView(
    modifier: Modifier = Modifier,
    viewModel: NoteItDownBaseViewModel,
    productDataModel: ProductDataModel,
    saveBlock: CompletionBlock<ProductDataModel>
) {
    val context = LocalContext.current
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
    val (productImageUrl, onProductImageUrl) = rememberSaveable {
        mutableStateOf(value = productDataModel.productImageUrl)
    }
    RequestProductImage(
        viewModel = viewModel,
        productLabel =
            if (productImageUrl == null
                && (productQuantity.replace(
                    oldValue = productDataModel.productQuantityType.code,
                    newValue = ""
            ).toDoubleOrNull() ?: 0.0) > 0.0)
                productLabel
            else
                null,
        successBlock = ProductImageUrl@ {
            productDataModel.productImageUrl =
                this@ProductImageUrl
            onProductImageUrl(
                this@ProductImageUrl
            )
        }
    )
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
                textStyle = MaterialTheme.typography.bodyLarge.medium
                        withTextAlign TextAlign.Center
                        withColor Color.White,
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
                            style = MaterialTheme.typography.labelSmall
                                    withTextAlign TextAlign.Center
                        )
                    innerTextField()
                },
                cursorBrush = SolidColor(
                    value = Color.White
                )
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
                textStyle = MaterialTheme.typography.labelMedium
                        withTextAlign TextAlign.Center
                        withColor Color.White,
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
                                    productImageUrl = productImageUrl,
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
                        style = MaterialTheme.typography.labelSmall
                                withTextAlign TextAlign.Center
                    )
                    innerTextField()
                },
                cursorBrush = SolidColor(
                    value = Color.White
                )
            )
        },
        icon = {
            if (!productImageUrl.isNullOrEmpty())
                AsyncImage(
                    modifier = Modifier
                        .clip(
                            shape = CircleShape
                        )
                        .size(
                            size = 28.dp
                        ),
                    model = context imageRequest productImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            else
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
        ),
        colors = ChipDefaults.chipColors(
            backgroundColor = productDataModel.backgroundColor
        )
    )
}

@Composable
private fun RequestProductImage(
    viewModel: NoteItDownBaseViewModel,
    productLabel: String?,
    successBlock: SuccessBlock<String>
) {
    LaunchedEffect(
        key1 = productLabel
    ) {
        if (!productLabel.isNullOrEmpty())
            viewModel.requestProductImage(
                product = productLabel,
                successBlock = successBlock
            )
    }
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
            viewModel = viewModel(),
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