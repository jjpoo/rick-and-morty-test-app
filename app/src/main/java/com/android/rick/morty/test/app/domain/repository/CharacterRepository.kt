package com.android.rick.morty.test.app.domain.repository

import androidx.paging.PagingData
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.SortOrder
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharacters(sortOrder: SortOrder? = null): Flow<PagingData<Character>>

    fun getCharacterById(characterId: Int): Flow<Character?>
}
