package com.goofy.goober.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goofy.goober.interactor.DatePlannerInteractor
import com.goofy.goober.model.DatePlannerIntent
import com.goofy.goober.model.DatePlannerState
import com.goofy.goober.model.DatePlannerUi
import com.goofy.goober.ui.state.DatePlannerScreenStates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal class DatePlannerViewModel(
    datePlannerInteractor: DatePlannerInteractor,
    private val datePlannerUi: DatePlannerUi,
    val screenStates: DatePlannerScreenStates
) : ViewModel() {

    val state: StateFlow<DatePlannerState> get() = datePlannerUi.state

    init {
        viewModelScope.launch {
            datePlannerUi.reduce(datePlannerInteractor.produceInitIntent())
        }
    }

    fun consumeIntent(intent: DatePlannerIntent) = datePlannerUi.reduce(intent)
}
