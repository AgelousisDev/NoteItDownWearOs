package com.agelousis.noteitdown.tiles.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.glance.state.GlanceStateDefinition
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper
import com.agelousis.noteitdown.utils.helpers.PreferencesStoreHelper.Companion.dataStore
import java.io.File

object CustomGlanceStateDefinition : GlanceStateDefinition<Preferences> {

    private const val fileName = PreferencesStoreHelper.NOTE_IT_DOWN_PREFERENCES_KEY

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<Preferences> {
        return context.dataStore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return File(context.applicationContext.filesDir, "datastore/$fileName")
    }
}