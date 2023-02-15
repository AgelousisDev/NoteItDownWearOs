package com.agelousis.noteitdown.utils.helpers

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.utils.extensions.jsonString
import com.agelousis.noteitdown.utils.extensions.toModelList
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class PreferencesStoreHelper(private val context: Context) {

    companion object {
        const val NOTE_IT_DOWN_PREFERENCES_KEY = "noteItDownPreferences"
        val Context.dataStore by preferencesDataStore(name = NOTE_IT_DOWN_PREFERENCES_KEY)

        val NOTE_DATA_DATA_KEY = stringPreferencesKey(name = "currentAddressData")
    }

    suspend infix fun addNote(
        noteDataModel: NoteDataModel
    ) {
        val noteDataModelArrayList = ArrayList(noteDataModelList.firstOrNull() ?: listOf())
        noteDataModelArrayList.add(noteDataModel)
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[NOTE_DATA_DATA_KEY] = noteDataModelArrayList.jsonString
                ?: ""
        }
    }

    val noteDataModelList
        get() = context.dataStore.data.map { preferences ->
            preferences[NOTE_DATA_DATA_KEY]?.takeIf { it.isNotEmpty() }?.toModelList<NoteDataModel>()
        }

}