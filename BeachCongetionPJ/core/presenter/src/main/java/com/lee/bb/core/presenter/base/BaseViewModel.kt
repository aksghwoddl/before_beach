package com.lee.bb.core.presenter.base

import androidx.lifecycle.ViewModel
import com.example.mvisampleapp.ui.base.BaseEvent
import com.example.mvisampleapp.ui.base.BaseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S : BaseState, E : BaseEvent>(
    initialState: S,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    abstract fun handleEvent(event: E)

    protected fun updateState(function: (S) -> S) {
        _state.update(function)
    }
}
