package com.goofy.goober.model

data class ImageLoadState(
    val hasError: Boolean = false,
    val isLoading: Boolean = false,
    val title: String = "",
    val imageUrl: String? = null
)
