package com.agelousis.noteitdown.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Note
import androidx.compose.ui.graphics.vector.ImageVector

data class NoteDataModel(
    val icon: ImageVector = Icons.Filled.Note,
    val tag: String?,
    val note: String?
)