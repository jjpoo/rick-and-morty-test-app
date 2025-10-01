package com.android.rick.morty.test.app.presentation.characters.state

import androidx.paging.PagingData
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.presentation.core.UiContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class CharactersUiContract : UiContract {

    sealed interface States : UiContract.State {
        data object Loading : States
        data class Success(
            val characters: Flow<PagingData<Character>> = flowOf(),
            val searchQuery: String = ""
        ) : States
        data class Error(val message: String? = "") : States
    }

    sealed interface Events : UiContract.Event {
        data class OnQueryChanged(val query: String) : Events
        data class OnSearchSubmitted(val query: String) : Events
        data class OnItemClicked(val character: Character) : Events
    }

    sealed interface Effects : UiContract.Effect {
        data class NavigateToDetails(val characterId: Int) : Effects
    }
}
