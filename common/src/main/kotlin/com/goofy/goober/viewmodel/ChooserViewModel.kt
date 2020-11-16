package com.goofy.goober.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.ArtInteractor
import com.goofy.goober.model.ChooserIntent
import com.goofy.goober.model.ChooserScreenState
import com.goofy.goober.model.QuestionsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChooserViewModel(
    private val interactor: ArtInteractor
) : ViewModel() {

    val state: StateFlow<ChooserScreenState> get() = _state
    private val _state: MutableStateFlow<ChooserScreenState> = MutableStateFlow(ChooserScreenState(
        questionState = QuestionsState()
    ))

    fun consumeIntent(intent: ChooserIntent) {
        reduce(intent)
        when (intent) {
            is ChooserIntent.LoadNext -> consumeIntent(interactor.produceNextIntent(intent))
            is ChooserIntent.LoadImage -> {
                viewModelScope.launch {
                    reduce(interactor.produceNextIntent(intent))
                }
            }
        }
    }

    private fun reduce(intent: ChooserIntent) {
        _state.value = _state.value.reduce(intent)
    }
}
