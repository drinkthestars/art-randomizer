package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import com.goofy.goober.ui.DatePlannerApp
import com.goofy.goober.ui.theme.DatePlannerAppTheme
import com.goofy.goober.ui.viewmodel.DatePlannerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@OptIn(ExperimentalCoroutinesApi::class)
class DatePlannerActivity : AppCompatActivity() {

    private val viewModel: DatePlannerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatePlannerAppTheme {
                val state = viewModel.state.collectAsState()

                DatePlannerApp(
                    state = state.value,
                    onIntent = { viewModel.consumeIntent(it) }
                )
            }
        }
    }
}
