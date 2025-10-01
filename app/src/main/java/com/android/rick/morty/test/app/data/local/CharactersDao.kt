package com.android.rick.morty.test.app.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterentity")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characterentity")
    suspend fun clearAll()

    @Query("SELECT * FROM characterentity WHERE id = :characterId")
    fun getCharacterById(characterId: Int): Flow<CharacterEntity?>
}