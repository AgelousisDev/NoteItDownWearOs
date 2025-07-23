/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.agelousis.noteitdown.noteItDown

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.tooling.preview.devices.WearDevices
import com.agelousis.noteitdown.noteItDown.navigation.NoteItDownNavigationScreen
import com.agelousis.noteitdown.noteItDown.ui.NoteItDownActivityView
import com.agelousis.noteitdown.ui.theme.NoteItDownTheme

class NoteItDownActivity: ComponentActivity() {

    companion object {

        const val NOTE_IT_DOWN_NAVIGATION_SCREEN_EXTRA = "NoteItDownActivity=noteItDownNavigationExtra"

        fun show(
            context: Context,
            noteItDownNavigationScreen: NoteItDownNavigationScreen,
            flags: Int? = null
        ) {
            context.startActivity(
                Intent(
                    context,
                    NoteItDownActivity::class.java
                ).also { intent ->
                    if (flags != null)
                        intent.flags = flags
                    intent.putExtra(
                        NOTE_IT_DOWN_NAVIGATION_SCREEN_EXTRA,
                        noteItDownNavigationScreen.route
                    )
                }
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteItDownTheme {
                NoteItDownActivityView()
            }
        }
    }
}


@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    NoteItDownActivityView()
}