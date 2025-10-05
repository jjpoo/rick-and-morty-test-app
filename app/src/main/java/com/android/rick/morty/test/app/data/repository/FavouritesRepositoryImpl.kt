package com.android.rick.morty.test.app.data.repository

import com.android.rick.morty.test.app.data.local.CharactersDatabase
import com.android.rick.morty.test.app.data.local.FavouriteEntity
import com.android.rick.morty.test.app.domain.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val database: CharactersDatabase
) : FavouritesRepository {

    override suspend fun addToFavorites(id: Int) {
        database.favouriteDao.addToFavourite(FavouriteEntity(id))
    }

    override suspend fun removeFromFavourites(id: Int) {
        database.favouriteDao.removeFromFavourite(FavouriteEntity(id))
    }

    override fun getCharacterFavStatus(id: Int): Flow<Boolean> {
        return database.favouriteDao.getFavouriteStatus(id)
    }
}