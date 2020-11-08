package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.WelcomeFragmentBinding
import com.goofy.goober.ui.state.bindState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeFragment : Fragment() {

    interface FragmentState {
        fun welcomeState(): StateFlow<State>
    }

    private val fragmentState: FragmentState by bindState()

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
        val onStartClick: View.OnClickListener
    )
}
