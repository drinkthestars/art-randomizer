package com.goofy.goober

import android.app.Application
import com.goofy.goober.factory.DatePlannerUiIntentConsumerFactory
import com.goofy.goober.interactor.DatePlannerInteractor
import com.goofy.goober.ui.state.DatePlannerScreenStates
import com.goofy.goober.model.DatePlannerUi
import com.goofy.goober.ui.viewmodel.DatePlannerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class DatePlannerApplication : Application() {

    private val appModule = module {

        factory { DatePlannerScreenStates() }

        factory { DatePlannerUiIntentConsumerFactory(applicationCoroutineScope = get()) }

        factory { DatePlannerInteractor() }

        factory { DatePlannerUi(externalEventConsumer = get<DatePlannerUiIntentConsumerFactory>().create()) }

        viewModel {
            DatePlannerViewModel(
                datePlannerUi = get(),
                datePlannerInteractor = get(),
                screenStates = get()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DatePlannerApplication)
            modules(appModule)
        }
    }
}
