package com.android.rick.morty.test.app.domain.usecase

import com.android.rick.morty.test.app.domain.repository.FavouritesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Changes favourite status for current character.
 * If character is in favourites -> removes from favourites
 * If character is not in favourites -> adds to favourites
 */

class ToggleFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavouritesRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        val isCurrentlyFavourite = favoritesRepository.getCharacterFavStatus(id).first()
        when (isCurrentlyFavourite) {
            true -> {
                favoritesRepository.removeFromFavourites(id)
                return false
            }

            false -> {
                favoritesRepository.addToFavorites(id)
                return true
            }
        }
    }
}