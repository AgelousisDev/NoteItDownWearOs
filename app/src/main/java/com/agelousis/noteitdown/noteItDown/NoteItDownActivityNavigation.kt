package com.agelousis.noteitdown.noteItDown

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.agelousis.noteitdown.noteItDown.navigation.NoteItDownNavigationScreen
import com.agelousis.noteitdown.noteItDown.ui.AddNoteScreenLayout
import com.agelousis.noteitdown.noteItDown.ui.NotesListScreenLayout
import com.agelousis.noteitdown.noteItDown.ui.ProductsWithQuantityScreenLayout
import com.agelousis.noteitdown.noteItDown.ui.RuleOfThreeLayout

@Composable
fun NoteItDownActivityNavigation(
    scalingLazyColumnState: ScalingLazyListState
) {
    val context = LocalContext.current
    val navController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = (context as? NoteItDownActivity)?.intent?.extras?.getString(
            NoteItDownActivity.NOTE_IT_DOWN_NAVIGATION_SCREEN_EXTRA
        ) ?: NoteItDownNavigationScreen.AddNoteScreen.route
    ) {
        composable(
            route = NoteItDownNavigationScreen.AddNoteScreen.route
        ) {
            AddNoteScreenLayout(
                scalingLazyColumnState = scalingLazyColumnState,
                notesListBlock = {
                    navController.navigate(
                        route = NoteItDownNavigationScreen.NotesListScreen.route
                    )
                },
                methodOfThreeBlock = {
                    navController.navigate(
                        route = NoteItDownNavigationScreen.RuleOfThreeScreen.route
                    )
                },
                productsWithQuantityBlock = {
                    navController.navigate(
                        route = NoteItDownNavigationScreen.ProductsWithQuantityScreen.route
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
        composable(
            route = NoteItDownNavigationScreen.ProductsWithQuantityScreen.route
        ) {
            ProductsWithQuantityScreenLayout(
                scalingLazyColumnState = scalingLazyColumnState
            )
        }
    }
}