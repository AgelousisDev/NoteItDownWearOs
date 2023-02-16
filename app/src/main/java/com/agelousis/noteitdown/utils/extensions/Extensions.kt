package com.agelousis.noteitdown.utils.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Parcelable
import androidx.core.os.BuildCompat
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

val isAndroid13
    @SuppressLint("UnsafeOptInUsageError")
    get() = BuildCompat.isAtLeastT()

inline fun <reified T: Parcelable> Intent.getParcelable(
    key: String
) = if (isAndroid13)
    extras?.getParcelable(key, T::class.java)
else
    extras?.getParcelable(key)

inline fun <reified T: java.io.Serializable> Intent.getSerializable(
    key: String
) = if (isAndroid13)
    extras?.getSerializable(key, T::class.java)
else
    extras?.getSerializable(key) as? T