package com.android.rick.morty.test.app.data.local.mapper

import com.android.rick.morty.test.app.core.ApiEnum
import com.android.rick.morty.test.app.data.local.CharacterEntity
import com.android.rick.morty.test.app.domain.model.Character

fun CharacterEntity.toCharacter() = Character(
    id = id,
    name = name,
    status = ApiEnum.fromString(status),
    species = ApiEnum.fromString(species),
    type = type,
    gender = ApiEnum.fromString(gender),
    origin = origin,
    location = location,
    image = image,
    episodes = if (episodes.isEmpty()) emptyList() else episodes.split(","),
    created = created
)