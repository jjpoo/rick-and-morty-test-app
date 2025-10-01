package com.android.rick.morty.test.app.data.remote.mapper

import com.android.rick.morty.test.app.data.remote.model.CharacterDto
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.Gender
import com.android.rick.morty.test.app.domain.model.Species
import com.android.rick.morty.test.app.domain.model.Status

fun CharacterDto.toDomain() = Character(
    id = this.id,
    name = this.name,
    status = Status.fromString(status),
    species = Species.fromString(species),
    type = this.type,
    gender = Gender.fromString(gender),
    origin = this.origin.name,
    location = this.location.name,
    image = this.image,
    episodes = this.episode,
    created = this.created,
    isFavorite = false
)
