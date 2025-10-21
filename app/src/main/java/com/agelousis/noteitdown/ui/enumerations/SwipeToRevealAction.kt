package com.agelousis.noteitdown.ui.enumerations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.ui.graphics.vector.ImageVector

enum class SwipeToRevealAction(
    val icon: ImageVector
) {
    DELETE(
        icon = Icons.Outlined.Delete
    );
}