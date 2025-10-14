package com.android.rick.morty.test.app.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OriginDto(
    val name: String,
    val url: String
)