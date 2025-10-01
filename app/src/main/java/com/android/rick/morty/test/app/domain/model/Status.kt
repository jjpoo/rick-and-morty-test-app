package com.android.rick.morty.test.app.domain.model

import com.android.rick.morty.test.app.core.ApiEnum

enum class Status(override val apiValue: String) : ApiEnum {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): Status = ApiEnum.fromString(value)
    }
}