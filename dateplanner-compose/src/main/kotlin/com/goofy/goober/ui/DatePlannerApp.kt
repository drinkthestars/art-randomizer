package com.goofy.goober.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goofy.goober.model.DatePlannerIntent
import com.goofy.goober.model.DatePlannerState
import com.goofy.goober.model.Question

@Composable
internal fun DatePlannerApp(state: DatePlannerState, onIntent: (DatePlannerIntent) -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        when (state) {
            is DatePlannerState.Loading -> UnInitializedScreen()
            is DatePlannerState.Welcome -> WelcomeScreen(onIntent)
            is DatePlannerState.StillCustomizing -> StillCustomizingScreen(
                state.currentQuestion,
                onIntent
            )
            is DatePlannerState.FinishedCustomizing -> FinishedCustomizingScreen(
                state.result,
                onIntent
            )
        }
    }
}

@Composable
internal fun WelcomeScreen(onIntent: (DatePlannerIntent) -> Unit) {
    FadeInCenterContentColumn {
        OptionButton(
            text = "Start",
            onIntent = onIntent,
            intent = DatePlannerIntent.ContinueCustomizing(
                question = Question.firstQuestion,
                previousChoice = null
            )
        )
    }
}

@Composable
internal fun StillCustomizingScreen(
    question: Question,
    onIntent: (DatePlannerIntent) -> Unit
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
            question.options.values.forEach {
                OptionButtonWithMargin(it, onIntent, question)
            }
        }
    }
}

@Composable
internal fun UnInitializedScreen() {
    val typography = MaterialTheme.typography
    FadeInCenterContentColumn {
        WrapContentBox { Text(text = "Loading...", style = typography.h5) }
        Spacer(Modifier.preferredHeight(20.dp))
        WrapContentBox { CircularProgressIndicator() }
    }
}

@Composable
internal fun FinishedCustomizingScreen(
    result: String,
    onIntent: (DatePlannerIntent) -> Unit
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
            intent = DatePlannerIntent.StartOver,
            onIntent = onIntent
        )
    }
}
