package com.android.rick.morty.test.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val status: Status,
    val species: Species,
    val type: String,
    val gender: Gender,
    val origin: String,
    val location: String,
    val created: String,
    val episodes: List<String>,
    val isFavorite: Boolean
) : Parcelable