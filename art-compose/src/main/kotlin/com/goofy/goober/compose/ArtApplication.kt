package com.goofy.goober.compose

import android.app.Application
import com.goofy.goober.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ArtApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ArtApplication)
            modules(commonModule)
        }
    }
}
