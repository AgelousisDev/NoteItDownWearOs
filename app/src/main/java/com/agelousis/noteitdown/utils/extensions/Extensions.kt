package com.agelousis.noteitdown.utils.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.toModel(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    }
    catch(e: Exception) {
        null
    }
}

inline fun <reified T> String.toModelList(): List<T>? {
    return try {
        Gson().fromJson(this, object: TypeToken<List<T>>() {}.type)
    }
    catch(e: Exception) {
        null
    }
}


val <T>T.jsonString
    get() = try {
        Gson().toJson(this)
    }
    catch(e: Exception) {
        null
    }