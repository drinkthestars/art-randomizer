package com.goofy.goober.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.goofy.goober.R
import com.goofy.goober.model.DatePlannerIntent
import com.goofy.goober.ui.navigation.NavRouter
import com.goofy.goober.ui.state.BackButtonPressHandler
import com.goofy.goober.ui.viewmodel.DatePlannerNavArgsViewModel
import com.goofy.goober.ui.viewmodel.DatePlannerViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

private val BACK_BUTTON_ACTION = DatePlannerIntent.GoBackToPrevious

class DatePlannerActivity : AppCompatActivity() {

    private val viewModel: DatePlannerViewModel by viewModel()
    private val navArgsViewModel: DatePlannerNavArgsViewModel by viewModel()
    private val backButtonPressHandler = object : BackButtonPressHandler {
        override fun onBackPress() {
            viewModel.consumeIntent(BACK_BUTTON_ACTION)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        val navRouter = NavRouter(
            navController = navController(),
            navArgsViewModel = navArgsViewModel
        )

        viewModel.state
            .onEach { state ->
                navRouter.route(
                    datePlannerState = state,
                    backButtonPressHandler = backButtonPressHandler,
                    onExit = { this.finish() }
                ) { intent ->
                    viewModel.consumeIntent(intent)
                }
            }.launchIn(lifecycleScope)
    }

    private fun navController(): NavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        return navHostFragment.navController
    }
}
