package com.android.rick.morty.test.app.core

interface ApiEnum {
    val apiValue: String

    companion object {
        inline fun <reified T> fromString(value: String): T
                where T : Enum<T>, T : ApiEnum {
            return enumValues<T>().find {
                it.apiValue.equals(value, ignoreCase = true)
            } ?: enumValues<T>().first()
        }
    }
}