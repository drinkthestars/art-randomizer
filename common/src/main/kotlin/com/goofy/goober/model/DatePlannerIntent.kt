package com.goofy.goober.model

sealed class DatePlannerIntent {

    object ShowWelcome : DatePlannerIntent()

    data class FinishCustomizing(val lastChoice: String) : DatePlannerIntent()

    data class ContinueCustomizing(
        val previousChoice: String?,
        val question: Question.Regular
    ) : DatePlannerIntent()

    object StartOver : DatePlannerIntent()

    object GoBackToPrevious : DatePlannerIntent()
}
