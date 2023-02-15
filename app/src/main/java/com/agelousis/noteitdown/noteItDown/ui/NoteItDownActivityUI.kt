package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.*
import com.agelousis.noteitdown.noteItDown.navigation.NoteIdDownNavigationScreen

@Composable
fun NoteItDownActivityNavigationControllerLayout() {
    val navController = rememberNavController()
    Scaffold(
        timeText = {
            TimeText(
                modifier = Modifier
                    .scrollAway(
                        scrollState = rememberScrollState()
                    )
            )
        },
        vignette = {
            Vignette(
                vignettePosition = VignettePosition.TopAndBottom
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NoteIdDownNavigationScreen.AddNoteScreen.route
        ) {
            composable(
                route = NoteIdDownNavigationScreen.AddNoteScreen.route
            ) {
                AddNoteScreenLayout {
                    navController.navigate(
                        NoteIdDownNavigationScreen.NotesListScreen.route
                    )
                }
            }
            composable(
                route = NoteIdDownNavigationScreen.NotesListScreen.route
            ) {
                NotesListScreenLayout {
                    navController.navigateUp()
                }
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NoteItDownActivityNavigationControllerLayoutPreview() {
    NoteItDownActivityNavigationControllerLayout()
}