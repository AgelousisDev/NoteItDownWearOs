package com.agelousis.noteitdown.noteItDown

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.agelousis.noteitdown.noteItDown.navigation.NoteItDownNavigationScreen
import com.agelousis.noteitdown.noteItDown.ui.AddNoteScreenView
import com.agelousis.noteitdown.noteItDown.ui.NotesListScreenView
import com.agelousis.noteitdown.noteItDown.ui.ProductsWithQuantityScreenView
import com.agelousis.noteitdown.noteItDown.ui.RuleOfThreeView
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel

@Composable
fun NoteItDownActivityNavigation(
    viewModel: NoteItDownBaseViewModel,
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
            AddNoteScreenView(
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
            NotesListScreenView(
                scalingLazyColumnState = scalingLazyColumnState
            )
        }
        composable(
            route = NoteItDownNavigationScreen.RuleOfThreeScreen.route
        ) {
            RuleOfThreeView(
                scalingLazyColumnState = scalingLazyColumnState
            )
        }
        composable(
            route = NoteItDownNavigationScreen.ProductsWithQuantityScreen.route
        ) {
            ProductsWithQuantityScreenView(
                viewModel = viewModel,
                scalingLazyColumnState = scalingLazyColumnState
            )
        }
    }
}