package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.databinding.WelcomeFragmentBinding
import com.goofy.goober.ui.state.bindState

class WelcomeFragment : Fragment() {

    // TODO: Eventually replace with StateFlow
    interface FragmentState {
        fun welcomeState(): LiveData<State>
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
                lifecycleOwner = viewLifecycleOwner
                state = fragmentState.welcomeState()
            }
            .root
    }

    data class State(
        val progressVisibility: Int,
        val welcomeVisibility: Int,
        val onStartClick: View.OnClickListener
    )
}
