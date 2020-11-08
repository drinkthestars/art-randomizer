package com.goofy.goober.ui.navigation

import android.view.View
import androidx.navigation.NavController
import com.goofy.goober.R
import com.goofy.goober.model.DatePlannerIntent
import com.goofy.goober.model.DatePlannerState
import com.goofy.goober.model.Question
import com.goofy.goober.model.intent
import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.state.BackButtonPressHandler
import com.goofy.goober.ui.view.QuestionView
import com.goofy.goober.ui.viewmodel.DatePlannerNavArgsViewModel

internal class NavRouter(
    private val navController: NavController,
    private val navArgsViewModel: DatePlannerNavArgsViewModel
) {
    fun route(
        datePlannerState: DatePlannerState,
        backButtonPressHandler: BackButtonPressHandler,
        onExit: () -> Unit,
        onIntent: (DatePlannerIntent) -> Unit
    ) {
        datePlannerState.routeInternal(backButtonPressHandler, onIntent, onExit)
    }

    private fun DatePlannerState.routeInternal(
        backButtonPressHandler: BackButtonPressHandler,
        onIntent: (DatePlannerIntent) -> Unit,
        onExit: () -> Unit
    ) {
        when (this) {
            DatePlannerState.Loading -> {
                val screenState = WelcomeFragment.State(
                    welcomeVisibility = View.GONE,
                    progressVisibility = View.VISIBLE,
                    onStartClick = { },
                    backButtonPressHandler = backButtonPressHandler
                )
                with(navController) {
                    navigate(R.id.welcomeFragment)
                    navArgsViewModel.updateWelcomeState(screenState)
                }
            }

            DatePlannerState.Welcome -> {
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
                    },
                    backButtonPressHandler = backButtonPressHandler
                )
                with(navController) {
                    navigate(R.id.welcomeFragment)
                    navArgsViewModel.updateWelcomeState(screenState)
                }
            }

            is DatePlannerState.StillCustomizing -> {
                val screenState = QuestionView.State(
                    question = this.currentQuestion,
                    clickListener = { text -> onIntent(this.currentQuestion.intent(choice = text)) },
                    backButtonPressHandler = backButtonPressHandler
                )
                with(navController) {
                    if (currentDestination?.id == R.id.welcomeFragment) {
                        navigate(R.id.action_welcomeFragment_to_questionFragment)
                    }
                    navArgsViewModel.updateQuestionState(screenState)
                }
            }

            is DatePlannerState.FinishedCustomizing -> {
                val screenState = EndFragment.State(this.result)
                with(navController) {
                    navigate(R.id.action_questionFragment_to_endFragment)
                    navArgsViewModel.updateEndState(screenState)
                }
            }

            DatePlannerState.Exited -> {
                onExit()
            }
        }
    }
}
