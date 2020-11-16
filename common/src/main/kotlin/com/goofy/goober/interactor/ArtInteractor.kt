package com.goofy.goober.interactor

import com.goofy.goober.api.model.Result
import com.goofy.goober.api.usecase.GetArtObject
import com.goofy.goober.model.ChooserIntent
import com.goofy.goober.model.Options
import com.goofy.goober.model.Question
import com.goofy.goober.model.Step
import com.goofy.goober.model.century
import com.goofy.goober.model.contains

class ArtInteractor(
    private val getArtObject: GetArtObject
) {

    suspend fun produceNextIntent(
        intent: ChooserIntent.LoadImage
    ): ChooserIntent {
        return when (val result = getArtObject(
            query = intent.query,
            geo = intent.geo,
            startDate = intent.startDate,
            endDate = intent.endDate
        )) {
            is Result.Success -> ChooserIntent.ShowImage(result.data)
            Result.Fail -> ChooserIntent.ShowImageError
        }
    }

    fun produceNextIntent(
        intent: ChooserIntent.LoadNext
    ): ChooserIntent {
        with(intent) {
            val newChoices = choicesSoFar + newChoice
            val newQuestion = currentQuestion.nextQuestion()

            return newQuestion
                ?.let { ChooserIntent.ShowNext(newChoices, newQuestion) }
                ?: ChooserIntent.LoadImage(
                    query = newChoices[1],
                    geo = newChoices[0],
                    startDate = newChoices[2].toInt(),
                    endDate = newChoices[2].toInt() + 100,
                )
        }
    }

    private fun Question.nextQuestion(): Question? {
        return when (step) {
            Step.Location -> Question(
                step = Step.Contains,
                value = "and one of these:",
                options = Options(contains)
            )
            Step.Contains -> Question(
                step = Step.Century,
                value = "pick a century:",
                options = Options(century)
            )
            Step.Century -> null
        }
    }
}
