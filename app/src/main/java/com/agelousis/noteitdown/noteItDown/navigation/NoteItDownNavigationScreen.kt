package com.agelousis.noteitdown.noteItDown.navigation

sealed class NoteItDownNavigationScreen(
    val route: String
) {

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

}