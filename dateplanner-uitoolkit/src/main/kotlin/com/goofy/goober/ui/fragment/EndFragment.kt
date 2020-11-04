package com.goofy.goober.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.goofy.goober.databinding.EndFragmentBinding
import com.goofy.goober.ui.state.bindState

class EndFragment : Fragment() {

    // TODO: Eventually replace with StateFlow
    interface FragmentState {
        fun endState(): LiveData<State>
    }

    private val fragmentState: FragmentState by bindState()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return EndFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                state = fragmentState.endState()
            }
            .root
    }

    data class State(val answer: String)
}
