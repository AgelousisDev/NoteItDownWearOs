package com.agelousis.noteitdown.noteItDown.navigation

sealed class NoteItDownNavigationScreen(
    val route: String
) {

    companion object {

        infix fun fromRoute(
            route: String?
        ) = NoteItDownNavigationScreen::class.sealedSubclasses.firstOrNull { noteItDownNavigationScreen ->
            noteItDownNavigationScreen.objectInstance?.route == route
        }?.objectInstance

    }

    data object AddNoteScreen: NoteItDownNavigationScreen(
        route = "AddNoteScreen"
    )

    data object NotesListScreen: NoteItDownNavigationScreen(
        route = "NotesListScreen"
    )

    data object RuleOfThreeScreen: NoteItDownNavigationScreen(
        route = "RuleOfThreeScreen"
    )

}