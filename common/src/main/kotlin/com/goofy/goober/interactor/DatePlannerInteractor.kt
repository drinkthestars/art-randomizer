package com.goofy.goober.interactor

import com.goofy.goober.model.DatePlannerIntent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DatePlannerInteractor {

    suspend fun produceInitIntent(): DatePlannerIntent = withContext(Dispatchers.IO) {
        // Do some work
        delay(2_000)

        DatePlannerIntent.ShowWelcome
    }
}
