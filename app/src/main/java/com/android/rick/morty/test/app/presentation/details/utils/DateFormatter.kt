package com.android.rick.morty.test.app.presentation.details.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun String.getCreationDate(): String {
    return try {
        val instant = Instant.parse(this)
        val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
        localDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"))
    } catch (e: DateTimeParseException) {
        this
    }
}