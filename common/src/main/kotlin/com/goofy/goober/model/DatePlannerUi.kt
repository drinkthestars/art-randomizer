package com.goofy.goober.model

import com.goofy.goober.model.DatePlannerState.Loading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
class DatePlannerUi(private val externalEventConsumer: (Transition) -> Unit) {
    val state: StateFlow<DatePlannerState> get() = _state

    private val _state: MutableStateFlow<DatePlannerState> = MutableStateFlow(Loading)

    operator fun invoke(block: DatePlannerUi.() -> Unit) = block()

    fun reduce(intent: DatePlannerIntent) {
        val fromState = _state.value
        val toState = fromState.reduce(intent)
        if (fromState != toState) _state.value = toState

        Transition(
            fromState = fromState,
            toState = toState,
            intent = intent
        ).also { externalEventConsumer(it) }
    }
}
