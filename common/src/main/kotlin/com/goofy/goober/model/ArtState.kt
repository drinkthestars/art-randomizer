package com.goofy.goober.model

sealed class ArtState {

    abstract fun reduce(intent: ArtIntent): ArtState

    object Welcome : ArtState() {
        override fun reduce(intent: ArtIntent): ArtState {
            return when (intent) {
                is ArtIntent.ShowChooser -> Chooser
                ArtIntent.ShowWelcome -> this
            }
        }
    }

    object Chooser : ArtState() {
        override fun reduce(intent: ArtIntent): ArtState {
            return when (intent) {
                is ArtIntent.ShowChooser -> this
                ArtIntent.ShowWelcome -> this
            }
        }
    }
}
