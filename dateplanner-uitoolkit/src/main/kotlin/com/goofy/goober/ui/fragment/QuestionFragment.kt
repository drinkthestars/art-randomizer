package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.QuestionFragmentBinding
import com.goofy.goober.ui.state.bindState
import com.goofy.goober.ui.util.activityArgs
import com.goofy.goober.ui.view.QuestionView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionFragment : Fragment() {

    interface FragmentState {
        fun questionState(): StateFlow<QuestionView.State>
    }

    private val fragmentState by activityArgs<FragmentState>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return QuestionFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                fragmentState.questionState()
                    .onEach { questionViewState = it }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
            .root
    }
}
