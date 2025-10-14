package com.android.rick.morty.test.app.presentation.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.android.rick.morty.test.app.domain.usecase.GetCharacterByIdUseCase
import com.android.rick.morty.test.app.domain.usecase.GetFavouriteStatusUseCase
import com.android.rick.morty.test.app.domain.usecase.ToggleFavoritesUseCase
import com.android.rick.morty.test.app.presentation.core.BaseViewModel
import com.android.rick.morty.test.app.presentation.core.UiContract
import com.android.rick.morty.test.app.presentation.details.state.DetailsUiContract
import com.android.rick.morty.test.app.presentation.details.utils.cleanOriginLocationName
import com.android.rick.morty.test.app.presentation.details.utils.getCreationDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val toggleFavoritesUseCase: ToggleFavoritesUseCase,
    private val getFavouriteStatusUseCase: GetFavouriteStatusUseCase
) : BaseViewModel<DetailsUiContract.State, DetailsUiContract.Effect>(initialState = DetailsUiContract.State.Loading) {

    override fun handleEvent(event: UiContract.Event) {
        when (event) {
            is DetailsUiContract.Event.OnBackClicked -> sendEffect(DetailsUiContract.Effect.NavigateBack)
            is DetailsUiContract.Event.OnFavClicked -> toggleFavourites(event.characterId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            try {
                combine(
                    getCharacterByIdUseCase(characterId),
                    getFavouriteStatusUseCase(characterId)
                ) { character, isFavourite ->
                    character to isFavourite
                }.collect { (character, isFavourite) ->
                    if (character != null) {
                        setState {
                            DetailsUiContract.State.Success(
                                character = character.copy(
                                    created = character.created.getCreationDate(),
                                    origin = character.origin.cleanOriginLocationName(),
                                    location = character.origin.cleanOriginLocationName()
                                ),
                                isFavourite = isFavourite
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                e.message?.let { error ->
                    sendEffect(DetailsUiContract.Effect.ShowErrorToast(error))
                }
            }
        }
    }

    fun handleDetailsEvent(event: DetailsUiContract.Event) {
        when (event) {
            is DetailsUiContract.Event.OnBackClicked -> {
                viewModelScope.launch {
                    sendEffect(DetailsUiContract.Effect.NavigateBack)
                }
            }

            is DetailsUiContract.Event.OnFavClicked -> {
                toggleFavourites(event.characterId)
            }
        }
    }

    private fun toggleFavourites(characterId: Int) {
        viewModelScope.launch {
            val isCharacterInFavourites = toggleFavoritesUseCase(characterId)
            val currentCharacter = (state.value as? DetailsUiContract.State.Success)?.character
            setState {
                DetailsUiContract.State.Success(
                    character = currentCharacter,
                    isFavourite = isCharacterInFavourites
                )
            }
        }
    }
}