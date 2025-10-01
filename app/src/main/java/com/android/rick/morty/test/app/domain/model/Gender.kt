package com.android.rick.morty.test.app.domain.model

import com.android.rick.morty.test.app.core.ApiEnum

enum class Gender(override val apiValue: String) : ApiEnum {
    FEMALE("Female"),
    MALE("Male"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): Gender = ApiEnum.fromString(value)
    }
}