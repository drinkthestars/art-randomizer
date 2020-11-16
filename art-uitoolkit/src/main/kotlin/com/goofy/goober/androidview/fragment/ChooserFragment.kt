package com.goofy.goober.androidview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.goofy.goober.androidview.view.ChooserView
import com.goofy.goober.databinding.ChooserFragmentBinding
import com.goofy.goober.model.ChooserIntent.*
import com.goofy.goober.model.QuestionsState
import com.goofy.goober.viewmodel.ChooserViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class ChooserFragment : Fragment() {

    private val viewModel by viewModel<ChooserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ChooserFragmentBinding
            .inflate(LayoutInflater.from(context), container, false)
            .apply {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    viewModel.state.collect { state ->
                        questionContent = state.questionState?.question?.value
                        imageLoadState = state.imageLoadState
                        imageLoadProps = View.OnClickListener {  viewModel.consumeIntent(StartOver) }
                        chooserState = ChooserView.State(
                            state.questionState
                        ) { questionState, choice ->
                            viewModel.consumeIntent(loadNext(questionState, choice))
                        }
                    }
                }
            }
            .root
    }

    private fun loadNext(
        questionState: QuestionsState,
        choice: String
    ): LoadNext {
        return LoadNext(
            choicesSoFar = questionState.choicesSoFar,
            currentQuestion = questionState.question,
            newChoice = choice
        )
    }
}
