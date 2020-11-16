package com.goofy.goober.androidview.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.goofy.goober.androidview.navigation.NavArgsViewModel

inline fun <reified T : Any> Fragment.activityArgs(): Lazy<T> {
    return lazy {
        val newArgsViewModel by activityViewModels<NavArgsViewModel>()
        checkNotNull(newArgsViewModel as? T) {
            "DatePlannerNavArgsViewModel not implemented with ${T::class}"
        }
    }
}
