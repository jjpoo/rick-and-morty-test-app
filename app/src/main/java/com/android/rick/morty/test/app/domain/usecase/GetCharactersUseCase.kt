package com.android.rick.morty.test.app.domain.usecase

import androidx.paging.PagingData
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.SortOrder
import com.android.rick.morty.test.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(sortOrder: SortOrder? = null): Flow<PagingData<Character>> {
        return characterRepository.getCharacters(sortOrder)
    }
}