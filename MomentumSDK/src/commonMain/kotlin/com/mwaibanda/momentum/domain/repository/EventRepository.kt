package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Event
import com.mwaibanda.momentum.utils.Result

interface EventRepository {
    suspend fun fetchAllEvents(): Result<List<Event>>
}