package com.agelousis.noteitdown.utils.helpers

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.agelousis.noteitdown.models.NoteDataModel
import com.agelousis.noteitdown.models.ProductDataModel
import com.agelousis.noteitdown.utils.extensions.jsonString
import com.agelousis.noteitdown.utils.extensions.toModelList
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.util.Collections

class PreferencesStoreHelper(private val context: Context) {

    companion object {
        const val NOTE_IT_DOWN_PREFERENCES_KEY = "noteItDownPreferences"
        val Context.dataStore by preferencesDataStore(name = NOTE_IT_DOWN_PREFERENCES_KEY)

        val NOTE_DATA_DATA_KEY = stringPreferencesKey(name = "currentAddressData")
        val PRODUCT_DATA_KEY = stringPreferencesKey(name = "productData")
    }

    //region Notes
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

    suspend infix fun setNoteAsFirst(
        noteDataModel: NoteDataModel
    ) {
        val noteDataModelList = noteDataModelList.firstOrNull() ?: listOf()
        Collections.swap(
            noteDataModelList,
            noteDataModelList.indexOf(noteDataModel),
            0
        )
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[NOTE_DATA_DATA_KEY] = noteDataModelList.jsonString
                ?: ""
        }
    }

    suspend infix fun removeNote(
        noteDataModel: NoteDataModel
    ) {
        val noteDataModelArrayList = ArrayList(noteDataModelList.firstOrNull() ?: listOf())
        noteDataModelArrayList.remove(noteDataModel)
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[NOTE_DATA_DATA_KEY] = noteDataModelArrayList.jsonString
                ?: ""
        }
    }

    val noteDataModelList
        get() = context.dataStore.data.map { preferences ->
            preferences[NOTE_DATA_DATA_KEY]?.takeIf { it.isNotEmpty() }?.toModelList<NoteDataModel>()
        }
    //endregion

    //region Products
    suspend infix fun saveProductData(
        productDataModel: ProductDataModel
    ) {
        val productDataModelList = this.productDataModelList.firstOrNull()?.toMutableList()
            ?: mutableListOf()
        productDataModelList.firstOrNull {
            it.id == productDataModel.id
        }?.update(
            productLabel = productDataModel.productLabel,
            productQuantity = productDataModel.productQuantity,
            productQuantityType = productDataModel.productQuantityType
        ) ?: productDataModelList.add(
            productDataModel withId ((productDataModelList.mapNotNull(
                transform = ProductDataModel::id
            ).maxOrNull() ?: 0) + 1)
        )
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[PRODUCT_DATA_KEY] = productDataModelList.jsonString
                ?: ""
        }
    }

    suspend infix fun removeProductData(
        productDataModel: ProductDataModel
    ) {
        val productDataModelList = this.productDataModelList.firstOrNull()?.toMutableList()
            ?: mutableListOf()
        productDataModelList.removeIf {
            it.id == productDataModel.id
        }
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[PRODUCT_DATA_KEY] = productDataModelList.jsonString
                ?: ""
        }
    }

    val productDataModelList
        get() = context.dataStore.data.map { preferences ->
            preferences[PRODUCT_DATA_KEY]?.takeIf { it.isNotEmpty() }?.toModelList<ProductDataModel>()
        }
    //endregion

}