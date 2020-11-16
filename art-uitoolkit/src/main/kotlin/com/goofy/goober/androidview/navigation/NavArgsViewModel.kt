package com.goofy.goober.androidview.navigation

import androidx.lifecycle.ViewModel
import com.goofy.goober.androidview.fragment.WelcomeFragment
import com.goofy.goober.androidview.view.ChooserView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class NavArgsViewModel : ViewModel(), ChildFragmentArgs {

    private val welcomeProps = MutableStateFlow<WelcomeFragment.Props?>(null)

    fun setWelcomeProps(newProps: WelcomeFragment.Props) {
        welcomeProps.value = newProps
    }

    override fun welcomeProps(): Flow<WelcomeFragment.Props> = welcomeProps.filterNotNull()
}

interface ChildFragmentArgs : WelcomeFragment.FragmentArgs
