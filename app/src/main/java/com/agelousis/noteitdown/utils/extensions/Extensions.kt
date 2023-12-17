package com.agelousis.noteitdown.utils.extensions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import com.agelousis.noteitdown.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

typealias CompletionBlock<T> = T.() -> Unit

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
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

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

infix fun Context.shareText(
    text: String
) {
    startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SEND).also { intent ->
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, text)
            },
            resources.getString(R.string.key_share_via_label)
        )
    )
}

val Float.asIntValue
    get() = if (rem(1f).equals(0.0f)) toInt().toString() else toString()

val Double.decimalPlaces
    get() = with(
        receiver = this.toString()
    ) {
        split(".").takeIf {
            it.size == 2
        }?.let {
            if (it[1].toIntOrNull() == 0)
                0
            else
                it[1].length
        } ?: 0
    }
