package com.goofy.goober.ui.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.QuestionFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.view.QuestionView

class DatePlannerScreenStates : PizzaChildFragmentStates {

    private val welcomeState = MutableLiveData<WelcomeFragment.State>()
    private val questionState = MutableLiveData<QuestionView.State>()
    private val endState = MutableLiveData<EndFragment.State>()

    fun updateWelcomeState(newState: WelcomeFragment.State) {
        welcomeState.value = newState
    }

    fun updateQuestionState(newState: QuestionView.State) {
        questionState.value = newState
    }

    fun updateEndState(newState: EndFragment.State) {
        endState.value = newState
    }

    override fun welcomeState(): LiveData<WelcomeFragment.State> = welcomeState
    override fun questionState(): LiveData<QuestionView.State> = questionState
    override fun endState(): LiveData<EndFragment.State> = endState
}

interface PizzaChildFragmentStates :
    WelcomeFragment.FragmentState,
    EndFragment.FragmentState,
    QuestionFragment.FragmentState
