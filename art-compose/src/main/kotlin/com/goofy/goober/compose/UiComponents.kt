package com.goofy.goober.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goofy.goober.common.R
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
internal fun ImageContent(imageUrl: String, title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CoilImage(
            contentDescription = title,
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
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            color = Color.Black,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(end = 80.dp)
        )
        Image(
            modifier = Modifier.size(40.dp)
                .align(Alignment.CenterEnd)
                .clickable(onClick = onStartOver),
            contentDescription = title,
            imageVector = Companion.vectorResource(R.drawable.ic_refresh)
        )
    }
}

@Composable
internal fun ImageLoadInProgress(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
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
            .height(50.dp),
        contentAlignment = Alignment.Center
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
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
internal fun WrapContentBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = Modifier.wrapContentSize().then(modifier),
        contentAlignment = Alignment.Center,
        content = content
    )
}
