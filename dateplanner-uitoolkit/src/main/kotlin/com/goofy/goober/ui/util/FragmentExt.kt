package com.goofy.goober.ui.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.goofy.goober.ui.viewmodel.DatePlannerNavArgsViewModel

inline fun <reified T : Any> Fragment.activityArgs(): Lazy<T> {
    return lazy {
        val newArgsViewModel by activityViewModels<DatePlannerNavArgsViewModel>()
        checkNotNull(newArgsViewModel as? T) {
            "DatePlannerNavArgsViewModel not implemented with ${T::class}"
        }
    }
}