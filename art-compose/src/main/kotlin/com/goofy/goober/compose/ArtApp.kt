package com.goofy.goober.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goofy.goober.model.ArtIntent
import com.goofy.goober.model.ArtState
import com.goofy.goober.model.ChooserIntent
import com.goofy.goober.model.ImageLoadState
import com.goofy.goober.model.QuestionsState
import com.goofy.goober.viewmodel.ChooserViewModel

@Composable
internal fun ArtApp(
    chooserViewModel: ChooserViewModel,
    state: ArtState,
    onIntent: (ArtIntent) -> Unit
) {
    Crossfade(state) {
        Surface(color = MaterialTheme.colors.background) {
            when (state) {
                is ArtState.Welcome -> WelcomeScreen { onIntent(ArtIntent.ShowChooser) }
                is ArtState.Chooser -> ChooserScreen(chooserViewModel)
            }
        }
    }
}

@Composable
internal fun WelcomeScreen(onIntent: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OptionButton(
            text = "Start",
            onClick = onIntent
        )
    }
}

@Composable
internal fun ChooserScreen(
    viewModel: ChooserViewModel
) {
    val state by viewModel.state.collectAsState()
    val typography = MaterialTheme.typography
    val questionsState = state.questionState
    val imageLoadState = state.imageLoadState
    if (questionsState != null) {
        with(questionsState) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WrapContentBox { Text(text = question.value, style = typography.h5) }
                Spacer(Modifier.height(20.dp))
                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    question.options.values.forEach {
                        OptionButtonWithMargin(it) { choice ->
                            viewModel.consumeIntent(loadNext(questionsState, choice))
                        }
                    }
                }
            }
        }
    } else if (imageLoadState != null) {
        ImageDetails(imageLoadState, onStartOver = {
            viewModel.consumeIntent(ChooserIntent.StartOver)
        })
    }
}

@Composable
internal fun ImageDetails(
    imageLoadState: ImageLoadState,
    onStartOver: () -> Unit
) {
    val imageUrl = imageLoadState.imageUrl
    when {
        imageUrl != null -> {
            Box(modifier = Modifier.fillMaxSize()) {
                ImageContent(imageUrl, imageLoadState.title)
                ImageTitleContent(imageLoadState.title, onStartOver)
            }
        }
        imageLoadState.isLoading -> ImageLoadInProgress()
        imageLoadState.hasError -> ImageLoadError()
    }
}

private fun loadNext(
    questionState: QuestionsState,
    choice: String
): ChooserIntent.LoadNext {
    return ChooserIntent.LoadNext(
        choicesSoFar = questionState.choicesSoFar,
        currentQuestion = questionState.question,
        newChoice = choice
    )
}
