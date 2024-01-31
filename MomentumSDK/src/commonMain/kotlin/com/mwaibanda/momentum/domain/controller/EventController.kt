package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.EventGroup
import com.mwaibanda.momentum.utils.DataResponse

interface EventController {
    fun getAllEvents(onCompletion: (DataResponse<List<EventGroup>>) -> Unit)
}