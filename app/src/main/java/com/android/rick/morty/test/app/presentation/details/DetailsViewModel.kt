package com.android.rick.morty.test.app.presentation.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.rick.morty.test.app.domain.usecase.GetCharacterByIdUseCase
import com.android.rick.morty.test.app.domain.usecase.GetFavouriteStatusUseCase
import com.android.rick.morty.test.app.domain.usecase.ToggleFavoritesUseCase
import com.android.rick.morty.test.app.presentation.details.state.DetailsUiContract
import com.android.rick.morty.test.app.presentation.details.utils.cleanOriginLocationName
import com.android.rick.morty.test.app.presentation.details.utils.getCreationDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val toggleFavoritesUseCase: ToggleFavoritesUseCase,
    private val getFavouriteStatusUseCase: GetFavouriteStatusUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<DetailsUiContract.State>(DetailsUiContract.State.Loading)
    val state: StateFlow<DetailsUiContract.State> = _state

    private val _effect = MutableSharedFlow<DetailsUiContract.Effect>()
    fun effect() = _effect

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
                        _state.value = DetailsUiContract.State.Success(
                            character = character.copy(
                                created = character.created.getCreationDate(),
                                origin = character.origin.cleanOriginLocationName(),
                                location = character.origin.cleanOriginLocationName()
                            ),
                            isFavourite = isFavourite
                        )
                    }
                }
            } catch (e: Exception) {
                e.message?.let { error ->
                    _effect.emit(DetailsUiContract.Effect.ShowErrorToast(error))
                }
            }
        }
    }

    fun handleDetailsEvent(event: DetailsUiContract.Event) {
        when (event) {
            is DetailsUiContract.Event.OnBackClicked -> {
                viewModelScope.launch {
                    _effect.emit(
                        DetailsUiContract.Effect.NavigateBack
                    )
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
            val currentCharacter = (_state.value as? DetailsUiContract.State.Success)?.character
            _state.value = DetailsUiContract.State.Success(
                character = currentCharacter,
                isFavourite = isCharacterInFavourites
            )
        }
    }
}