package com.goofy.goober.model

import com.goofy.goober.ext.makeAnswer
import com.goofy.goober.model.DatePlannerIntent.*

sealed class DatePlannerState {

    abstract fun reduce(intent: DatePlannerIntent): DatePlannerState

    object Loading: DatePlannerState() {
        override fun reduce(intent: DatePlannerIntent): DatePlannerState {
            return when(intent) {
                ShowWelcome -> Welcome
                GoBackToPrevious -> Exited

                StartOver,
                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }

    object Welcome: DatePlannerState() {
        override  fun reduce(intent: DatePlannerIntent): DatePlannerState {
            return when(intent) {
                is ContinueCustomizing -> StillCustomizing(
                    choicesMadeSoFar = emptyList(),
                    currentQuestion = intent.question
                )
                GoBackToPrevious -> Exited

                StartOver,
                ShowWelcome,
                is FinishCustomizing -> this
            }
        }
    }

    data class StillCustomizing(
        val choicesMadeSoFar: List<String>,
        val currentQuestion: Question
    ) : DatePlannerState() {
        override  fun reduce(intent: DatePlannerIntent): DatePlannerState {
            return when(intent) {
                is ContinueCustomizing -> {
                    val newChoices = intent.previousChoice
                        ?.let { choicesMadeSoFar + it }
                        ?: choicesMadeSoFar
                    StillCustomizing(
                        newChoices,
                        currentQuestion = intent.question.nextQuestion()
                    )
                }
                is FinishCustomizing -> {
                    val allChoices = choicesMadeSoFar + intent.lastChoice
                    FinishedCustomizing(allChoices.makeAnswer())
                }
                GoBackToPrevious -> Welcome

                StartOver,
                ShowWelcome -> this
            }
        }
    }

    data class FinishedCustomizing(val result: String) : DatePlannerState() {
        override  fun reduce(intent: DatePlannerIntent): DatePlannerState {
            return when(intent) {
                StartOver -> Welcome
                GoBackToPrevious -> Exited

                ShowWelcome,
                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }

    object Exited : DatePlannerState() {
        override fun reduce(intent: DatePlannerIntent): DatePlannerState {
            return when (intent) {
                StartOver,
                ShowWelcome,
                GoBackToPrevious,
                is ContinueCustomizing,
                is FinishCustomizing -> this
            }
        }
    }
}

data class Transition(
    val fromState: DatePlannerState,
    val toState: DatePlannerState,
    val intent: DatePlannerIntent
)
