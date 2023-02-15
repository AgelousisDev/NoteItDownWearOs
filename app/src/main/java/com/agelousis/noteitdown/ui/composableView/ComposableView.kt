package com.agelousis.noteitdown.ui.composableView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.agelousis.noteitdown.ui.theme.textViewAlertTitleFont

typealias SimpleButtonBlock = () -> Unit

@Composable
fun SimpleButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    simpleButtonBlock: SimpleButtonBlock
) {
    Button(
        modifier = modifier,
        onClick = simpleButtonBlock
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp
            )
        ) {
            if (icon != null)
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            Text(
                text = text,
                style = textViewAlertTitleFont
            )
        }
    }
}

@Composable
fun SimpleButtonPreview() {
    SimpleButton(
        text = "Simple Button"
    ) {}
}