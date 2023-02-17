package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.agelousis.noteitdown.noteItDown.navigation.NoteItDownNavigationScreen
import kotlinx.coroutines.delay

@Composable
fun NoteItDownActivityNavigationControllerLayout(
    noteItDownNavigationScreen: NoteItDownNavigationScreen? = null
) {
    val navController = rememberSwipeDismissableNavController()
    LaunchedEffect(
        key1 = noteItDownNavigationScreen
    ) {
        if (noteItDownNavigationScreen == null)
            return@LaunchedEffect
        delay(
            timeMillis = 1000
        )
        navController.navigate(
            NoteItDownNavigationScreen.NotesListScreen.route
        )
    }
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
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = NoteItDownNavigationScreen.AddNoteScreen.route
        ) {
            composable(
                route = NoteItDownNavigationScreen.AddNoteScreen.route
            ) {
                AddNoteScreenLayout {
                    navController.navigate(
                        NoteItDownNavigationScreen.NotesListScreen.route
                    )
                }
            }
            composable(
                route = NoteItDownNavigationScreen.NotesListScreen.route
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