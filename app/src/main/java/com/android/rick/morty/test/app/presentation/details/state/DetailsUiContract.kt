package com.android.rick.morty.test.app.presentation.details.state

import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.presentation.core.UiContract

sealed class DetailsUiContract : UiContract {

    sealed interface State : UiContract.State {
        data object Loading : State
        data class Success(
            val character: Character? = null,
            val isFavourite: Boolean = false
        ) : State

        data class Error(val message: String = "") : State
    }

    sealed interface Event : UiContract.Event {
        data object OnBackClicked : Event
        data class OnFavClicked(val characterId: Int) : Event
    }

    sealed class Effect : UiContract.Effect {
        data object NavigateBack : Effect()
        data class ShowErrorToast(val message: String) : Effect()
    }
}