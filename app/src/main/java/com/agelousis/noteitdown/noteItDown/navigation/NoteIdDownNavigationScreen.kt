package com.agelousis.noteitdown.noteItDown.navigation

sealed class NoteIdDownNavigationScreen(
    val route: String
) {

    object AddNoteScreen: NoteIdDownNavigationScreen(
        route = "AddNoteScreen"
    )

    object NotesListScreen: NoteIdDownNavigationScreen(
        route = "NotesListScreen"
    )

}