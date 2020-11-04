package com.goofy.goober.ui

import androidx.compose.animation.animatedFloat
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Alignment
import androidx.compose.ui.DrawLayerModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goofy.goober.model.DatePlannerIntent
import com.goofy.goober.model.Question
import com.goofy.goober.model.intent

@Composable
internal fun OptionButton(
    text: String,
    onIntent: (DatePlannerIntent) -> Unit,
    intent: DatePlannerIntent
) {
    val typography = MaterialTheme.typography
    Button(onClick = { onIntent(intent) }) {
        Text(text = text, style = typography.button)
    }
}

@Composable
internal fun OptionButtonWithMargin(
    text: String,
    onIntent: (DatePlannerIntent) -> Unit,
    question: Question = Question.firstQuestion
) {
    Column {
        OptionButton(
            text = text,
            onIntent = onIntent,
            intent = question.intent(choice = text)
        )
        Spacer(Modifier.preferredHeight(10.dp))
    }
}

@Composable
internal fun WrapContentBox(
    modifier: Modifier = Modifier,
    children: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = Modifier.wrapContentSize().then(modifier),
        alignment = Alignment.Center,
        children = children
    )
}

@Composable
internal fun FadeInCenterContentColumn(
    modifier: Modifier = Modifier.fillMaxSize(),
    children: @Composable ColumnScope.() -> Unit
) {
    val alpha = animatedFloat(initVal = 0f)
    val layerModifier = object : DrawLayerModifier {
        override val alpha: Float get() = alpha.value
    }
    Column(
        modifier = layerModifier.then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        children = children
    )
    onCommit {
        alpha.animateTo(1f)
    }
}
