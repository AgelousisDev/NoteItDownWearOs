package com.agelousis.noteitdown.noteItDown.navigation

sealed class NoteItDownNavigationScreen(
    val route: String
) {

    companion object {
        const val PRODUCT_IMAGE_URL_KEY = "productImage"
    }

    data object BasicMenuScreen: NoteItDownNavigationScreen(
        route = "BasicMenuScreen"
    )

    data object AddNoteScreen: NoteItDownNavigationScreen(
        route = "AddNoteScreen"
    )

    data object NotesListScreen: NoteItDownNavigationScreen(
        route = "NotesListScreen"
    )

    data object RuleOfThreeScreen: NoteItDownNavigationScreen(
        route = "RuleOfThreeScreen"
    )

    data object ProductsWithQuantityScreen: NoteItDownNavigationScreen(
        route = "ProductsWithQuantityScreen"
    )

    data object ProductImagePreviewScreen: NoteItDownNavigationScreen(
        route = "ProductImagePreviewScreen?$PRODUCT_IMAGE_URL_KEY={$PRODUCT_IMAGE_URL_KEY}"
    )

}