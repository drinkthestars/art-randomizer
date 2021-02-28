package com.goofy.goober.compose.activity

import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.goofy.goober.compose.ArtApp
import com.goofy.goober.compose.theme.ArtAppTheme
import com.goofy.goober.viewmodel.ArtViewModel
import com.goofy.goober.viewmodel.ChooserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ArtActivity : AppCompatActivity() {

    private val viewModel: ArtViewModel by viewModel()
    private val chooserViewModel: ChooserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContent {
            ArtAppTheme {
                val state by viewModel.state.collectAsState()
                ArtApp(
                    chooserViewModel = chooserViewModel,
                    state = state,
                    onIntent = { viewModel.consumeIntent(it) }
                )
            }
        }
    }
}
