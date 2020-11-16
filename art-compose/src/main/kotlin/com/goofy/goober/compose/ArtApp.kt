package com.goofy.goober.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goofy.goober.model.ArtIntent
import com.goofy.goober.model.ArtState
import com.goofy.goober.model.Question

@Composable
internal fun ArtApp(state: ArtState, onIntent: (ArtIntent) -> Unit) {
    Crossfade(state) {
        Surface(color = MaterialTheme.colors.background) {
            when (state) {
                is ArtState.Welcome -> WelcomeScreen {
                    onIntent(
                        ArtIntent.ShowNext(
                            newQuestion = Question.firstQuestion,
                            newChoices = emptyList()
                        )
                    )
                }
                is ArtState.ShowingQuestion -> StillCustomizingScreen(
                    question = state.currentQuestion,
                    onChoiceClick = {
                        onIntent(
                            ArtIntent.LoadNext(
                                currentQuestion = state.currentQuestion,
                                newChoice = it,
                                choicesMadeSoFar = state.choicesMadeSoFar
                            )
                        )
                    }
                )
                is ArtState.FinishedCustomizing -> FinishedCustomizingScreen(
                    result = state.result,
                    onRestart = { onIntent(ArtIntent.StartOver) }
                )
            }
        }
    }
}

@Composable
internal fun WelcomeScreen(onIntent: (String) -> Unit) {
    FadeInCenterContentColumn {
        OptionButton(
            text = "Start",
            onClick = onIntent
        )
    }
}

@Composable
internal fun StillCustomizingScreen(
    question: Question,
    onChoiceClick: (String) -> Unit
) {
    val typography = MaterialTheme.typography
    FadeInCenterContentColumn {
        WrapContentBox { Text(text = question.value, style = typography.h5) }
        Spacer(Modifier.preferredHeight(20.dp))
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            question.options.values.forEach { OptionButtonWithMargin(it, onChoiceClick) }
        }
    }
}

@Composable
internal fun FinishedCustomizingScreen(
    result: String,
    onRestart: (String) -> Unit
) {
    FadeInCenterContentColumn {
        Text(
            text = result,
            modifier = Modifier.padding(horizontal = 30.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.preferredHeight(16.dp))
        OptionButton(
            text = "Start Over",
            onClick = onRestart
        )
    }
}
