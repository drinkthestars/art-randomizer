package com.goofy.goober.androidview.navigation

import com.goofy.goober.R
import com.goofy.goober.androidview.fragment.WelcomeFragment
import com.goofy.goober.model.ArtIntent
import com.goofy.goober.model.ArtState

internal class ArtRouter(
    private val artNavController: ArtNavController,
    private val navArgsViewModel: NavArgsViewModel
) {
    fun route(
        artState: ArtState,
        onIntent: (ArtIntent) -> Unit
    ) {
        artState.routeInternal(onIntent)
    }

    private fun ArtState.routeInternal(
        onIntent: (ArtIntent) -> Unit
    ) {
        when (this) {
            ArtState.Welcome -> {
                val props = WelcomeFragment.Props(
                    onStartClick = {
                        onIntent(ArtIntent.ShowChooser)
                    }
                )
                artNavController {
                    navArgsViewModel.setWelcomeProps(props)
                    navigate(R.id.welcomeFragment)
                }
            }

            is ArtState.Chooser -> {
                artNavController {
                    if (currentDestination?.id == R.id.welcomeFragment) {
                        navigate(R.id.action_welcomeFragment_to_chooserFragment)
                    }
                }
            }
        }
    }
}
