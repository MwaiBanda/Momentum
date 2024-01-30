package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Event
import com.mwaibanda.momentum.utils.DataResponse

interface EventRepository {
    suspend fun fetchAllEvents(): DataResponse<List<Event>>
}