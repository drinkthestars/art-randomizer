package com.goofy.goober.factory

import android.util.Log
import com.goofy.goober.model.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * Example of a consumer that wishes to listen for UI events and do some independent
 * tracking/processing that does not affect the UI, such as firing analytics events.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DatePlannerUiIntentConsumerFactory(
    // TODO: Use qualifier
    val applicationCoroutineScope: CoroutineScope
) {

    private val channel = Channel<Transition>()

    init {
        applicationCoroutineScope.launch {
            channel.consumeEach { datePlannerTransition ->
                Log.d(
                    "ActionConsumerFactory",
                    "Got ${datePlannerTransition.intent.javaClass.simpleName} intent"
                )
            }
        }
    }

    fun create(): (Transition) -> Unit {
        return { datePlannerTransition ->
            applicationCoroutineScope.launch { channel.send(datePlannerTransition) }
        }
    }
}
