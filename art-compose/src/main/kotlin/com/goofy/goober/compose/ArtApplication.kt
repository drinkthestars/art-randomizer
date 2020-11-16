package com.goofy.goober.compose

import android.app.Application
import com.goofy.goober.commonModule
import com.goofy.goober.viewmodel.ChooserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ArtApplication : Application() {

    private val appModule = module {
        viewModel { ChooserViewModel(interactor = get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ArtApplication)
            modules(commonModule)
            modules(appModule)
        }
    }
}
