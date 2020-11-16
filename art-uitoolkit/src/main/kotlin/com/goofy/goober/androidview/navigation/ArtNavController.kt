package com.goofy.goober.androidview.navigation

import androidx.navigation.NavController as AndroidNavController

class ArtNavController(
    private val androidNavController: AndroidNavController
) {
    operator fun invoke(block: AndroidNavController.() -> Unit) = androidNavController.block()
}
