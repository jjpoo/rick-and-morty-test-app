package com.android.rick.morty.test.app.presentation.characters.state

import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.SortOrder
import com.android.rick.morty.test.app.presentation.characters.model.SortOrderItem
import com.android.rick.morty.test.app.presentation.core.UiContract

sealed class CharactersUiContract : UiContract {

    sealed interface State : UiContract.State {
        data object Loading : State
        data class Success(
            val searchInput: String = "",
            val selectedSortOrder: SortOrder? = null,
            val sortOrders: List<SortOrderItem> = listOf(
                SortOrderItem(SortOrder.ASC, false),
                SortOrderItem(SortOrder.DESC, false)
            )
        ) : State
        data class Error(val message: String? = "") : State
    }

    sealed interface Event : UiContract.Event {
        data class OnInputChanged(val input: String) : Event
        data class OnItemClicked(val character: Character) : Event
        data object OnSortCharactersClicked : Event
    }

    sealed interface Effect : UiContract.Effect {
        data class NavigateToDetails(val characterId: Int) : Effect
        data class ShowErrorToast(val message: String) : Effect
    }
}
