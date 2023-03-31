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

    object AddNoteScreen: NoteItDownNavigationScreen(
        route = "AddNoteScreen"
    )

    object NotesListScreen: NoteItDownNavigationScreen(
        route = "NotesListScreen"
    )

    object QrCodesListScreen: NoteItDownNavigationScreen(
        route = "QrCodesListScreen"
    )

}