package com.goofy.goober.ui

import android.view.View
import com.goofy.goober.model.DatePlannerIntent
import com.goofy.goober.model.DatePlannerState
import com.goofy.goober.model.Question
import com.goofy.goober.model.intent
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.navigation.NavRouter
import com.goofy.goober.ui.navigation.Screen
import com.goofy.goober.ui.view.QuestionView

internal class DatePlannerRenderer(
    private val navRouter: NavRouter
) {

    fun render(
        datePlannerState: DatePlannerState,
        onIntent: (DatePlannerIntent) -> Unit
    ) {
        datePlannerState.renderInternal(onIntent)
    }

    private fun DatePlannerState.renderInternal(onIntent: (DatePlannerIntent) -> Unit) {
        when (this) {
            DatePlannerState.Loading -> {
                loading()
            }
            DatePlannerState.Welcome -> {
                welcomeScreen(onIntent)
            }
            is DatePlannerState.StillCustomizing -> {
                ongoing(onIntent, this.currentQuestion)
            }
            is DatePlannerState.FinishedCustomizing -> {
                ended(answer = this.result)
            }
        }.let {}
    }

    private fun loading() {
        val screenState = WelcomeFragment.State(
            welcomeVisibility = View.GONE,
            progressVisibility = View.VISIBLE,
            onStartClick = { }
        )
        navRouter.navigateTo(Screen.Welcome(screenState))
    }

    private fun welcomeScreen(onIntent: (DatePlannerIntent) -> Unit) {
        val screenState = WelcomeFragment.State(
            welcomeVisibility = View.VISIBLE,
            progressVisibility = View.GONE,
            onStartClick = {
                onIntent(
                    DatePlannerIntent.ContinueCustomizing(
                        previousChoice = null,
                        question = Question.firstQuestion
                    )
                )
            }
        )
        navRouter.navigateTo(Screen.Welcome(screenState))
    }

    private fun ongoing(
        onIntent: (DatePlannerIntent) -> Unit,
        question: Question
    ) {
        val screenState = QuestionView.State(
            question = question,
            clickListener = { text -> onIntent(question.intent(choice = text)) }
        )
        navRouter.navigateTo(Screen.Question(screenState))
    }

    private fun ended(answer: String) {
        val screenState = EndFragment.State(answer)
        navRouter.navigateTo(Screen.End(screenState))
    }
}
