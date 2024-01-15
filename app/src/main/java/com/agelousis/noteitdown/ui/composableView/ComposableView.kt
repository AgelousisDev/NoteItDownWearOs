package com.agelousis.noteitdown.ui.composableView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import kotlinx.coroutines.delay

typealias BorderedTextFieldValueChangedBlock = String.() -> Unit

@Composable
fun BorderedTextField(
    modifier: Modifier = Modifier,
    customShape: Shape = CircleShape,
    customBorderColor: Color = MaterialTheme.colorScheme.primaryContainer,
    value: String,
    borderedTextFieldValueChangedBlock: BorderedTextFieldValueChangedBlock
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var onDoneState by remember {
        mutableStateOf(value = false)
    }
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = customBorderColor,
                shape = customShape
            )
            .then(
                other = modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                if (!onDoneState)
                    borderedTextFieldValueChangedBlock(it)
                onDoneState = false
            },
            textStyle = MaterialTheme.typography.labelMedium.merge(
                other = TextStyle(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDoneState = true
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun AnimatedLayout(
    index: Int? = null,
    initialState: Boolean = false,
    state: Boolean = true,
    enter: EnterTransition = slideInHorizontally { it },
    exit: ExitTransition = slideOutHorizontally { -it },
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    var visibleState by remember {
        mutableStateOf(
            value = initialState
        )
    }
    AnimatedVisibility(
        visible = visibleState,
        enter = enter,
        exit = exit,
        content = content
    )
    LaunchedEffect(
        key1 = state
    ) {
        delay(
            timeMillis = (index ?: 1) * 200L
        )
        visibleState = state
    }
}

@Preview
@Composable
fun BorderedTextFieldPreview() {
    BorderedTextField(
        modifier = Modifier
            .size(
                width = 300.dp,
                height = 50.dp
            ),
        customBorderColor = MaterialTheme.colorScheme.secondary,
        value = "BorderedTextField",
        borderedTextFieldValueChangedBlock = {}
    )
}