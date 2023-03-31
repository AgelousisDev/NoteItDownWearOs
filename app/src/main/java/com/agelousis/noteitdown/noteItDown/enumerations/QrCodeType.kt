package com.agelousis.noteitdown.noteItDown.enumerations

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import com.agelousis.noteitdown.R
import com.agelousis.noteitdown.models.NoteDataModel

enum class QrCodeType {
    DRIVING_LICENSE_FRONT,
    DRIVING_LICENSE_BACK;

    companion object {

        infix fun getNoteDataModelList(
            context: Context
        ) = values().map { qrCodeType ->
            NoteDataModel(
                icon = Icons.Filled.QrCode,
                tag = qrCodeType.name,
                note = qrCodeType getLabelWith context
            )
        }

    }

    infix fun getLabelWith(
        context: Context
    ): String = context.resources.getStringArray(R.array.key_qr_code_types)[ordinal]
}