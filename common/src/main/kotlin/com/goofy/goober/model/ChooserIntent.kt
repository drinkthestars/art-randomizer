package com.goofy.goober.model

import com.goofy.goober.api.model.Image

sealed class ChooserIntent {

    data class LoadImage(
        val query: String,
        val geo: String,
        val startDate: Int,
        val endDate: Int
    ) : ChooserIntent()

    data class ShowImage(
        val image: Image
    ) : ChooserIntent()

    object ShowImageError : ChooserIntent()

    data class LoadNext(
        val choicesSoFar: List<String>,
        val currentQuestion: Question,
        val newChoice: String,
    ) : ChooserIntent()

    data class ShowNext(
        val newChoices: List<String>,
        val newQuestion: Question
    ) : ChooserIntent()

    object StartOver: ChooserIntent()
}
