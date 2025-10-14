package com.android.rick.morty.test.app.presentation.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.SortOrder
import com.android.rick.morty.test.app.domain.usecase.GetCharactersUseCase
import com.android.rick.morty.test.app.presentation.characters.state.CharactersUiContract
import com.android.rick.morty.test.app.presentation.core.BaseViewModel
import com.android.rick.morty.test.app.presentation.core.UiContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : BaseViewModel<CharactersUiContract.State, CharactersUiContract.Effect>(initialState = CharactersUiContract.State.Loading) {

    init {
        setState {
            CharactersUiContract.State.Success(searchInput = "")
        }
    }

    private val _selectedCharacterId = MutableStateFlow<Int?>(null)

    var charactersFlow: Flow<PagingData<Character>> =
        getCharactersUseCase().cachedIn(viewModelScope).catch { e ->
            e.message?.let {
                sendEffect(
                    CharactersUiContract.Effect.ShowErrorToast(it)
                )
            }
        }

    override fun handleEvent(event: UiContract.Event) {
        when (event) {
            is CharactersUiContract.Event.OnInputChanged -> {
                setState {
                    CharactersUiContract.State.Success(
                        searchInput = event.input
                    )
                }
            }

            is CharactersUiContract.Event.OnItemClicked -> {
                _selectedCharacterId.value = event.character.id
                viewModelScope.launch {
                    sendEffect(CharactersUiContract.Effect.NavigateToDetails(event.character.id))
                }
            }

            is CharactersUiContract.Event.OnSortCharactersClicked -> {
                sortCharacters()
            }
        }
    }

    private fun sortCharacters() {
        val currentState = state.value
        if (currentState is CharactersUiContract.State.Success) {
            val newSortOrder = when (currentState.selectedSortOrder) {
                SortOrder.ASC -> SortOrder.DESC
                SortOrder.DESC -> SortOrder.ASC
                null -> SortOrder.ASC
            }

            charactersFlow = getCharactersUseCase(newSortOrder).cachedIn(viewModelScope)

            val updatedSortOrders = currentState.sortOrders.map {
                it.copy(isEnabled = it.sortOrder == newSortOrder)
            }

            setState {
                currentState.copy(
                    selectedSortOrder = newSortOrder,
                    sortOrders = updatedSortOrders
                )
            }
        }
    }
}