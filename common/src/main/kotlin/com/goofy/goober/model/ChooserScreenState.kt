package com.goofy.goober.model

import com.goofy.goober.api.model.Image

data class ChooserScreenState(
    val questionState: QuestionsState? = null,
    val imageLoadState: ImageLoadState? = null
) {

    fun reduce(intent: ChooserIntent): ChooserScreenState {
        return when (intent) {
            is ChooserIntent.LoadImage -> {
                ChooserScreenState(imageLoadState = ImageLoadState(isLoading = true))
            }
            is ChooserIntent.ShowImage -> {
                ChooserScreenState(
                    imageLoadState = ImageLoadState(
                        isLoading = false,
                        imageUrl = intent.image.mainUrl,
                        title = "${intent.image.title} by ${intent.image.artist}"
                    )
                )
            }
            is ChooserIntent.ShowImageError -> {
                ChooserScreenState(
                    imageLoadState = ImageLoadState(hasError = true)
                )
            }
            is ChooserIntent.ShowNext -> {
                ChooserScreenState(
                    questionState = QuestionsState(
                        choicesSoFar = intent.newChoices,
                        question = intent.newQuestion
                    )
                )
            }
            is ChooserIntent.LoadNext -> this
        }
    }
}
