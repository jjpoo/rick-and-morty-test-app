package com.android.rick.morty.test.app.presentation.core

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UiContract.State, E : UiContract.Effect>(initialState: S) :
    ViewModel() {

    private val _state = mutableStateOf(initialState)
    val state: State<S> = _state

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    abstract fun handleEvent(event: UiContract.Event)

    protected fun sendEffect(effect: E) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    protected fun setState(reduce: S.() -> S) {
        _state.value = _state.value.reduce()
    }
}