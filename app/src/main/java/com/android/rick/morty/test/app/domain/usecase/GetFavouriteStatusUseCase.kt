package com.android.rick.morty.test.app.domain.usecase

import com.android.rick.morty.test.app.domain.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteStatusUseCase @Inject constructor(
    private val repository: FavouritesRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> {
        return repository.getCharacterFavStatus(id)
    }
}