package com.goofy.goober.compose.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import com.goofy.goober.compose.ArtApp
import com.goofy.goober.compose.theme.ArtAppTheme
import com.goofy.goober.viewmodel.ArtViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ArtActivity : AppCompatActivity() {

    private val viewModel: ArtViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtAppTheme {
                val state by viewModel.state.collectAsState()
                ArtApp(
                    state = state,
                    onIntent = { viewModel.consumeIntent(it) }
                )
            }
        }
    }
}
