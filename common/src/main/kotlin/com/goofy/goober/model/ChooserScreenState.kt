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
                        title = title(intent)
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
            is ChooserIntent.StartOver -> ChooserScreenState(questionState = QuestionsState())
            is ChooserIntent.LoadNext -> this
        }
    }

    private fun title(intent: ChooserIntent.ShowImage): String {
        return if (intent.image.artist.isNotBlank()) {
            "${intent.image.title} by ${intent.image.artist}"
        } else {
            intent.image.title
        }
    }
}
