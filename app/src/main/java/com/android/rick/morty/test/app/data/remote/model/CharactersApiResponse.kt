package com.android.rick.morty.test.app.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharactersApiResponse(
    @Json(name = "info")
    val infoDto: InfoDto,
    val results: List<CharacterDto>
)
