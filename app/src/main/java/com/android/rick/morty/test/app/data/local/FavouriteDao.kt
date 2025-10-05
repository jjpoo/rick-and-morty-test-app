package com.android.rick.morty.test.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourite(favourite: FavouriteEntity)

    @Delete
    suspend fun removeFromFavourite(favourite: FavouriteEntity)

    @Query("SELECT EXISTS (SELECT 1 FROM favourites WHERE characterId = :characterId)")
    fun getFavouriteStatus(characterId: Int): Flow<Boolean>
}