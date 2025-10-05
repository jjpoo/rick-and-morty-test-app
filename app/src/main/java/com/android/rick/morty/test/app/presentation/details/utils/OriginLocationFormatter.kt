package com.android.rick.morty.test.app.presentation.details.utils

fun String.cleanOriginLocationName(): String {
    return this.substringBefore(" (").trim()
}