package com.android.rick.morty.test.app.domain

import android.util.Log
import androidx.paging.PagingData
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<PagingData<Character>> {
        val res = characterRepository.getCharacters()
        return res
    }
}