package com.android.rick.morty.test.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 3)
abstract class CharactersDatabase() : RoomDatabase() {
    abstract val dao: CharactersDao
}