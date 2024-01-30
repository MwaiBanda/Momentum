package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.GroupedEvent
import com.mwaibanda.momentum.utils.DataResponse

interface EventController {
    fun getAllEvents(onCompletion: (DataResponse<List<GroupedEvent>>) -> Unit)
}