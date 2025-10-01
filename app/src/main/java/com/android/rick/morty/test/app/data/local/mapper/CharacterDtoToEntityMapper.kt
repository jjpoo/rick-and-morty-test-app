package com.android.rick.morty.test.app.data.local.mapper

import com.android.rick.morty.test.app.data.local.CharacterEntity
import com.android.rick.morty.test.app.data.remote.model.CharacterDto

fun CharacterDto.toEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin.name,
    location = location.name,
    image = image,
    episodes = episode.joinToString(","),
    created = created
)

