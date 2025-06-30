package com.agelousis.noteitdown.noteItDown.enumerations

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.noteItDown.ui.ButtonBlock

enum class NoteItDownManagementChip(
    val icon: ImageVector,
    val tint: Color
) {
    SAVE(
        icon = Icons.Filled.Save,
        tint = Color.Green.copy(
            alpha = 0.5f
        )
    ),
    NOTES_LIST(
        icon = Icons.AutoMirrored.Filled.List,
        tint = Color.Yellow.copy(
            alpha = 0.5f
        )
    ),
    RULE_OF_THREE(
        icon = Icons.Filled.Calculate,
        tint = Color.Cyan.copy(
            alpha = 0.5f
        )
    ),
    PRODUCTS_WITH_QUANTITY(
        icon = Icons.Filled.MonitorWeight,
        tint = Color.Red.copy(
            alpha = 0.5f
        )
    );

    infix fun label(
        resources: Resources
    ): String = resources.getStringArray(R.array.key_management_chip_labels_array)[ordinal]

    fun action(
        saveBlock: ButtonBlock,
        notesListBlock: ButtonBlock,
        methodOfThreeBlock: ButtonBlock,
        productsWithQuantityBlock: ButtonBlock
    ) {
        when (this) {
            SAVE ->
                saveBlock()
            NOTES_LIST ->
                notesListBlock()
            RULE_OF_THREE ->
                methodOfThreeBlock()
            PRODUCTS_WITH_QUANTITY ->
                productsWithQuantityBlock()
        }
    }

}