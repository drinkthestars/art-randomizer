package com.goofy.goober.model

import com.goofy.goober.model.Step.Entertainment
import com.goofy.goober.model.Step.Fragrance
import com.goofy.goober.model.Step.Mood
import com.goofy.goober.model.Step.TimeOfDay

enum class Step {
    Mood, Entertainment, Fragrance, TimeOfDay
}

sealed class Question {
    abstract val value: String
    abstract val options: Options

    companion object {
        val firstQuestion = Regular(
            step = Mood,
            value = "choose a mood:",
            options = Options(mood)
        )
    }

    class Regular(
        override val value: String,
        override val options: Options,
        val step: Step
    ) : Question() {

        fun nextQuestion(): Question {
            return when (step) {
                Mood -> Regular(
                    step = Entertainment,
                    value = "And one of these:",
                    options = Options(entertainment)
                )
                Entertainment -> Regular(
                    step = Fragrance,
                    value = "Pick a fragrance...",
                    options = Options(fragrance)
                )
                Fragrance -> Final(
                    value = "And finally pick a time of day",
                    options = Options(timeOfDay)
                )
                TimeOfDay -> Final(
                    value = "And finally pick a time of day",
                    options = Options(timeOfDay)
                )
            }
        }
    }

    data class Final(
        override val value: String,
        override val options: Options
    ) : Question()
}

data class Options(val values: List<String>)

fun Question.intent(choice: String): DatePlannerIntent {
    return when (this) {
        is Question.Regular -> {
            DatePlannerIntent.ContinueCustomizing(previousChoice = choice, question = this)
        }
        is Question.Final -> {
            DatePlannerIntent.FinishCustomizing(lastChoice = choice)
        }
    }
}

private val mood = listOf(
    "romantic",
    "chill",
    "fun",
    "engaging",
)
private val entertainment = listOf(
    "video games",
    "movies",
    "tv",
    "vid chat",
)
private val fragrance = listOf(
    "smoky",
    "citrus",
    "fresh",
    "cocoa"
)
private val timeOfDay = listOf(
    "morning",
    "afternoon",
    "evening",
    "night",
)
