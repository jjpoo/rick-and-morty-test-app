package com.android.rick.morty.test.app.presentation.details.state

import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.presentation.core.UiContract

sealed class DetailsUiContract : UiContract {

    sealed interface States : UiContract.State {
        data object Loading : States
        data class Success(val character: Character? = null) : States
        data class Error(val message: String = "") : States
    }

    sealed interface Events : UiContract.Event {
        data object OnBackClicked : Events
        data class OnFavClicked(
            val characterId: Int,
            val currentState: Boolean
        ) : Events
    }

    sealed class Effects : UiContract.Effect {
        data object NavigateBack : Effects()
    }
}