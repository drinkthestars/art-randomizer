package com.goofy.goober.model

sealed class ArtIntent {
    object ShowWelcome : ArtIntent()
    object ShowChooser : ArtIntent()
}
