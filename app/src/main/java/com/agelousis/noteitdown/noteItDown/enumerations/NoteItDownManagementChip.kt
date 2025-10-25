package com.agelousis.noteitdown.noteItDown.enumerations

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.NoteAdd
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.graphics.shapes.RoundedPolygon
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.ui.extensions.ButtonBlock

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
enum class NoteItDownManagementChip(
    val icon: ImageVector,
    val shape: RoundedPolygon
) {
    PRODUCTS_WITH_QUANTITY(
        icon = Icons.Filled.MonitorWeight,
        shape = MaterialShapes.Oval
    ),
    RULE_OF_THREE(
        icon = Icons.Filled.Calculate,
        shape = MaterialShapes.Arrow
    ),
    ADD_NOTE(
        icon = Icons.AutoMirrored.Outlined.NoteAdd,
        shape = MaterialShapes.Burst
    ),
    NOTES_LIST(
        icon = Icons.AutoMirrored.Filled.List,
        shape = MaterialShapes.ClamShell
    );

    infix fun label(
        resources: Resources
    ): String = resources.getStringArray(R.array.key_management_chip_labels_array)[ordinal]

    fun action(
        addNoteBlock: ButtonBlock,
        notesListBlock: ButtonBlock,
        methodOfThreeBlock: ButtonBlock,
        productsWithQuantityBlock: ButtonBlock
    ) {
        when (this) {
            ADD_NOTE ->
                addNoteBlock()
            NOTES_LIST ->
                notesListBlock()
            RULE_OF_THREE ->
                methodOfThreeBlock()
            PRODUCTS_WITH_QUANTITY ->
                productsWithQuantityBlock()
        }
    }

}