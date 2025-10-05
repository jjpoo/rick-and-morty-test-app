package com.android.rick.morty.test.app.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.SortOrder
import com.android.rick.morty.test.app.domain.usecase.GetCharactersUseCase
import com.android.rick.morty.test.app.presentation.characters.state.CharactersUiContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<CharactersUiContract.State>(CharactersUiContract.State.Loading)
    val state: StateFlow<CharactersUiContract.State> = _state

    private val _effect = MutableSharedFlow<CharactersUiContract.Effect>()
    fun getNavigationFlow() = _effect

    private val _selectedCharacterId = MutableStateFlow<Int?>(null)

    var charactersFlow: Flow<PagingData<Character>> =
        getCharactersUseCase()
            .cachedIn(viewModelScope)
            .catch { e ->
                e.message?.let {
                    _effect.emit(
                        CharactersUiContract.Effect.ShowErrorToast(it)
                    )
                }
            }

    init {
        _state.value = CharactersUiContract.State.Success(searchInput = "")
    }

    fun handleCharactersEvent(event: CharactersUiContract.Event) {
        val currentState = _state.value
        when (event) {
            is CharactersUiContract.Event.OnInputChanged -> {
                if (currentState is CharactersUiContract.State.Success) {
                    _state.value = currentState.copy(
                        searchInput = event.input,
                    )
                }
            }

            is CharactersUiContract.Event.OnItemClicked -> {
                _selectedCharacterId.value = event.character.id
                viewModelScope.launch {
                    _effect.emit(
                        CharactersUiContract.Effect.NavigateToDetails(event.character.id)
                    )
                }
            }

            is CharactersUiContract.Event.OnSortCharactersClicked -> {
                sortCharacters()
            }
        }
    }

    private fun sortCharacters() {
        val currentState = _state.value
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

            _state.value = currentState.copy(
                selectedSortOrder = newSortOrder,
                sortOrders = updatedSortOrders
            )
        }
    }
}