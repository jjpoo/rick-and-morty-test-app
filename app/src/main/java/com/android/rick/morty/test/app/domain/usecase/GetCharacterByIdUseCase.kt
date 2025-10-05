package com.android.rick.morty.test.app.domain.usecase

import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(characterId: Int): Flow<Character?> {
        return characterRepository.getCharacterById(characterId)
    }
}