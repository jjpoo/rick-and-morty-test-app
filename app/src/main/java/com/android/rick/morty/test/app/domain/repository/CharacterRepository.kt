package com.android.rick.morty.test.app.domain.repository

import androidx.paging.PagingData
import com.android.rick.morty.test.app.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(): Flow<PagingData<Character>>

    fun getCharacterById(characterId: Int): Flow<Character?>
}
