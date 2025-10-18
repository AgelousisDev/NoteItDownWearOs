package com.agelousis.noteitdown.noteItDown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.wear.compose.foundation.lazy.TransformingLazyColumnState
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.agelousis.noteitdown.noteItDown.navigation.NoteItDownNavigationScreen
import com.agelousis.noteitdown.noteItDown.ui.AddNoteScreenView
import com.agelousis.noteitdown.noteItDown.ui.BasicMenuScreenView
import com.agelousis.noteitdown.noteItDown.ui.NotesListScreenView
import com.agelousis.noteitdown.noteItDown.ui.ProductImagePreviewScreenView
import com.agelousis.noteitdown.noteItDown.ui.ProductsWithQuantityScreenView
import com.agelousis.noteitdown.noteItDown.ui.RuleOfThreeView
import com.agelousis.noteitdown.noteItDown.viewModel.NoteItDownBaseViewModel

@Composable
fun NoteItDownActivityNavigation(
    contentPadding: PaddingValues,
    viewModel: NoteItDownBaseViewModel,
    transformingLazyColumnState: TransformingLazyColumnState
) {
    val context = LocalContext.current
    val navController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = (context as? NoteItDownActivity)?.intent?.extras?.getString(
            NoteItDownActivity.NOTE_IT_DOWN_NAVIGATION_SCREEN_EXTRA
        ) ?: NoteItDownNavigationScreen.BasicMenuScreen.route
    ) {
        composable(
            route = NoteItDownNavigationScreen.BasicMenuScreen.route
        ) {
            BasicMenuScreenView(
                contentPadding = contentPadding,
                transformingLazyColumnState = transformingLazyColumnState,
                addNoteBlock = {
                    navController.navigate(
                        route = NoteItDownNavigationScreen.AddNoteScreen.route
                    )
                },
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
            route = NoteItDownNavigationScreen.AddNoteScreen.route
        ) {
            AddNoteScreenView(
                contentPadding = contentPadding,
                transformingLazyColumnState = transformingLazyColumnState
            )
        }
        composable(
            route = NoteItDownNavigationScreen.NotesListScreen.route
        ) {
            NotesListScreenView(
                contentPadding = contentPadding,
                transformingLazyColumnState = transformingLazyColumnState
            )
        }
        composable(
            route = NoteItDownNavigationScreen.RuleOfThreeScreen.route
        ) {
            RuleOfThreeView(
                contentPadding = contentPadding,
                transformingLazyColumnState = transformingLazyColumnState
            )
        }
        composable(
            route = NoteItDownNavigationScreen.ProductsWithQuantityScreen.route
        ) {
            ProductsWithQuantityScreenView(
                contentPadding = contentPadding,
                viewModel = viewModel,
                transformingLazyColumnState = transformingLazyColumnState,
                productImagePreviewBlock = ProductImageUrl@ {
                    navController.navigate(
                        route = NoteItDownNavigationScreen.ProductImagePreviewScreen.route
                            .replace(
                                oldValue = "{${NoteItDownNavigationScreen.PRODUCT_IMAGE_URL_KEY}}",
                                newValue = this@ProductImageUrl
                            )
                    )
                }
            )
        }
        composable(
            route = NoteItDownNavigationScreen.ProductImagePreviewScreen.route
        ) { backStackEntry ->
            ProductImagePreviewScreenView(
                productImageUrl = backStackEntry.arguments?.getString(
                    NoteItDownNavigationScreen.PRODUCT_IMAGE_URL_KEY
                )
            )
        }
    }
}