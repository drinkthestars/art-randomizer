package com.goofy.goober.compose

import androidx.compose.animation.animatedFloat
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Alignment
import androidx.compose.ui.DrawLayerModifier
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goofy.goober.common.R
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun ImageContent(imageUrl: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Center
    ) {
        CoilImage(
            contentScale = ContentScale.Crop,
            data = imageUrl,
            fadeIn = true,
            loading = { ImageLoadInProgress(modifier = Modifier.align(Alignment.Center)) },
            error = { ImageLoadError() }
        )
    }
}

@Composable
internal fun BoxScope.ImageTitleContent(title: String, onStartOver: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0x70FFFFFF))
            .padding(16.dp)
            .align(Alignment.BottomCenter),
        alignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            color = Color.Black,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(end = 80.dp)
        )
        Image(
            modifier = Modifier.preferredSize(40.dp)
                .align(Alignment.CenterEnd)
                .clickable(onClick = onStartOver),
            asset = vectorResource(R.drawable.ic_refresh)
        )
    }
}

@Composable
internal fun ImageLoadInProgress(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.Center
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .preferredHeight(50.dp),
            textAlign = TextAlign.Center,
            text = "Loading..."
        )
    }
}

@Composable
internal fun ImageLoadError() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(50.dp),
        alignment = Alignment.Center
    ) {
        Text(text = ":( Couldn't load")
    }
}

@Composable
internal fun OptionButton(
    text: String,
    onClick: (String) -> Unit
) {
    Button(onClick = { onClick(text) }) {
        Text(text = text, style = MaterialTheme.typography.button)
    }
}

@Composable
internal fun OptionButtonWithMargin(
    text: String,
    onClick: (String) -> Unit
) {
    Column {
        OptionButton(text, onClick)
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
