package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.ScheduledEvent
import com.mwaibanda.momentum.utils.Result

interface EventController {
    fun getAllEvents(onCompletion: (Result<List<ScheduledEvent>>) -> Unit)
}