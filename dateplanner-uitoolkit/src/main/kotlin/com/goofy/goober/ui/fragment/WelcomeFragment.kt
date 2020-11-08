package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.WelcomeFragmentBinding
import com.goofy.goober.ui.state.BackButtonPressHandler
import com.goofy.goober.ui.state.bindState
import com.goofy.goober.ui.util.activityArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeFragment : Fragment() {

    interface FragmentState {
        fun welcomeState(): StateFlow<State>
    }

    private val fragmentState by activityArgs<FragmentState>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragmentState.welcomeState().value.backButtonPressHandler?.onBackPress()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return WelcomeFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                fragmentState.welcomeState()
                    .onEach { state = it }
                    .launchIn(viewLifecycleOwner.lifecycleScope)
            }
            .root
    }

    data class State(
        val progressVisibility: Int,
        val welcomeVisibility: Int,
        val onStartClick: View.OnClickListener,
        val backButtonPressHandler: BackButtonPressHandler? = null
    )
}
