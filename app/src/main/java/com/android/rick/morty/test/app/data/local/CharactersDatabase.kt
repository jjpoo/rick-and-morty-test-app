package com.android.rick.morty.test.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class, FavouriteEntity::class], version = 4)
abstract class CharactersDatabase() : RoomDatabase() {
    abstract val charactersDao: CharactersDao
    abstract val favouriteDao: FavouriteDao
}