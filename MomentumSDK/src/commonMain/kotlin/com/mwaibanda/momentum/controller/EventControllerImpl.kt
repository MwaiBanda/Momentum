package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.EventController
import com.mwaibanda.momentum.domain.models.GroupedEvent
import com.mwaibanda.momentum.domain.usecase.event.GetEventsUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventControllerImpl: EventController, KoinComponent {
    private val getEventsUseCase: GetEventsUseCase by inject()
    private val mainScope = MainScope()
    override fun getAllEvents(onCompletion: (Result<List<GroupedEvent>>) -> Unit) {
        mainScope.launch {
            getEventsUseCase {
                onCompletion(it)
            }
        }
    }
}