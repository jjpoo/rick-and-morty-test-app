package com.android.rick.morty.test.app.domain.model

import com.android.rick.morty.test.app.core.ApiEnum

enum class Species(override val apiValue: String) : ApiEnum {
    HUMAN("Human"),
    ALIEN("Alien");

    companion object {
        fun fromString(value: String): Species = ApiEnum.fromString(value)
    }
}