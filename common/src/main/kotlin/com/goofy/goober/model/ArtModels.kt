package com.goofy.goober.model

import com.goofy.goober.model.Step.Location

enum class Step {
    Location, Contains, Century
}

data class Question(
    val value: String,
    val options: Options,
    val step: Step
) {
    companion object {
        val firstQuestion = Question(
            step = Location,
            value = "choose a location:",
            options = Options(location)
        )
    }
}

data class Options(val values: List<String>)

internal val location = listOf(
    "France",
    "China",
    "Spain",
    "Italy",
)
internal val contains = listOf(
    "flowers",
    "blue",
    "yellow",
    "plants"
)
internal val century = listOf(
    "1600",
    "1700",
    "1800",
    "1900"
)

fun List<String>.makeAnswer(): String {
    return "https://collectionapi.metmuseum.org/public/collection/v1/search?hasImages=true&geoLocation=" +
        "${this[0]}&dateBegin=${this[2]}&dateEnd=${this[2].toInt() + 100}&q=${this[1]}"
}
