package com.goofy.goober.ui.navigation

import com.goofy.goober.ui.fragment.EndFragment
import com.goofy.goober.ui.fragment.WelcomeFragment
import com.goofy.goober.ui.view.QuestionView

sealed class Screen<out STATE: Any> {
    abstract val state: STATE

    data class Welcome(override val state: WelcomeFragment.State): Screen<WelcomeFragment.State>()

    data class Question(override val state: QuestionView.State): Screen<QuestionView.State>()

    data class End(override val state: EndFragment.State): Screen<EndFragment.State>()
}
