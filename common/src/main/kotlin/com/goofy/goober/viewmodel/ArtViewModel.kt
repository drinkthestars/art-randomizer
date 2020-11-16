package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import com.goofy.goober.model.ArtIntent
import com.goofy.goober.model.ArtState
import com.goofy.goober.model.ArtState.Welcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArtViewModel : ViewModel() {

    val state: StateFlow<ArtState> get() = _state
    private val _state: MutableStateFlow<ArtState> = MutableStateFlow(Welcome)

    fun consumeIntent(intent: ArtIntent) {
        _state.value = _state.value.reduce(intent)
    }
}
