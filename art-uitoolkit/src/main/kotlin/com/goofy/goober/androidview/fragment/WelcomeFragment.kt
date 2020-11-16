package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.databinding.WelcomeFragmentBinding
import com.goofy.goober.androidview.util.activityArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class WelcomeFragment : Fragment() {

    interface FragmentArgs {
        fun welcomeProps(): Flow<Props>
    }

    private val args by activityArgs<FragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return WelcomeFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    args.welcomeProps()
                        .collect { props = it }
                }
            }
            .root
    }

    data class Props(
        val onStartClick: View.OnClickListener
    )
}
