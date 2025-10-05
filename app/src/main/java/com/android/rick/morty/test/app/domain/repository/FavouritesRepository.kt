package com.android.rick.morty.test.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun addToFavorites(id: Int)

    suspend fun removeFromFavourites(id: Int)

    fun getCharacterFavStatus(id: Int): Flow<Boolean>
}