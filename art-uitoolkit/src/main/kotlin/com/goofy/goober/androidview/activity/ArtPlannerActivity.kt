package com.goofy.goober.androidview.activity

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.goofy.goober.R
import com.goofy.goober.androidview.navigation.ArtNavController
import com.goofy.goober.androidview.navigation.ArtRouter
import com.goofy.goober.androidview.navigation.NavArgsViewModel
import com.goofy.goober.viewmodel.ArtViewModel
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class ArtPlannerActivity : AppCompatActivity() {

    private val viewModel: ArtViewModel by viewModel()
    private val navArgsViewModel: NavArgsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.main_activity)

        val router = ArtRouter(
            artNavController = navController(),
            navArgsViewModel = navArgsViewModel
        )

        lifecycleScope.launchWhenStarted {
            viewModel.state
                .collect { state ->
                    router.route(state) { viewModel.consumeIntent(it) }
                }
        }
    }

    private fun navController(): ArtNavController {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        return ArtNavController(navHostFragment.navController)
    }
}
