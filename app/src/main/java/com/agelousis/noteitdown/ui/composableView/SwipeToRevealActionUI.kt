package com.agelousis.noteitdown.ui.composableView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material3.FilledTonalIconButton
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.RevealState
import androidx.wear.compose.material3.RevealValue
import androidx.wear.compose.material3.rememberRevealState
import com.agelousis.noteitdown.ui.enumerations.SwipeToRevealAction
import com.agelousis.noteitdown.ui.extensions.ButtonBlock
import kotlinx.coroutines.launch

@Composable
fun SwipeToRevealActionView(
    modifier: Modifier = Modifier,
    revealState: RevealState,
    swipeToRevealAction: SwipeToRevealAction,
    swipeToRevealActionBlock: ButtonBlock
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .clickable(
                    onClick = {
                        coroutineScope.launch {
                            revealState.animateTo(
                                targetValue = RevealValue.RightRevealed
                            )
                        }
                    }
                ),
        contentAlignment = Alignment.Center,
    ) {
        FilledTonalIconButton(
            onClick = swipeToRevealActionBlock
        ) {
            Icon(
                imageVector = swipeToRevealAction.icon,
                contentDescription = swipeToRevealAction.name
            )
        }
    }
}

@Preview
@Composable
fun SwipeToRevealActionViewPreview() {
    SwipeToRevealActionView(
        revealState = rememberRevealState(),
        swipeToRevealAction = SwipeToRevealAction.DELETE,
        swipeToRevealActionBlock = {}
    )
}