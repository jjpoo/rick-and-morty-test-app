package com.android.rick.morty.test.app.data.remote.model

data class CharactersApiResponse(
    val info: Info,
    val results: List<CharacterDto>
)