package com.agelousis.noteitdown.noteItDown.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
            noteItDownNavigationScreen.route
        )
    }
    val scalingLazyColumnState = androidx.wear.compose.foundation.lazy.rememberScalingLazyListState()
    Scaffold(
        timeText = {
            TimeText(
                modifier = Modifier
                    .scrollAway(
                        scrollState = scalingLazyColumnState
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
                AddNoteScreenLayout(
                    scalingLazyColumnState = scalingLazyColumnState,
                    notesListBlock = {
                        navController.navigate(
                            NoteItDownNavigationScreen.NotesListScreen.route
                        )
                    },
                    methodOfThreeBlock = {
                        navController.navigate(
                            NoteItDownNavigationScreen.RuleOfThreeScreen.route
                        )
                    }
                )
            }
            composable(
                route = NoteItDownNavigationScreen.NotesListScreen.route
            ) {
                NotesListScreenLayout(
                    scalingLazyColumnState = scalingLazyColumnState
                )
            }
            composable(
                route = NoteItDownNavigationScreen.RuleOfThreeScreen.route
            ) {
                RuleOfThreeLayout(
                    scalingLazyColumnState = scalingLazyColumnState
                )
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun NoteItDownActivityNavigationControllerLayoutPreview() {
    NoteItDownActivityNavigationControllerLayout()
}