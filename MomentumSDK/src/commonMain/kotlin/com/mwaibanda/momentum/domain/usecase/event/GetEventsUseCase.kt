package com.mwaibanda.momentum.domain.usecase.event

import com.mwaibanda.momentum.domain.models.ScheduledEvent
import com.mwaibanda.momentum.domain.repository.EventRepository
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.getFormattedDate

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(onCompletion: (Result<List<ScheduledEvent>>) -> Unit) {
        when(val eventsResponse = eventRepository.fetchAllEvents()) {
            is Result.Failure -> {
                println("[GetEventsUseCase] ${eventsResponse.message ?: ""}")
            }
            is Result.Success -> {
                val events = eventsResponse.data ?: emptyList()
                onCompletion(Result.Success(events.groupBy { getFormattedDate(it.startTime, "mm yyyy") }.map { ScheduledEvent(it.key, it.value) }))
            }
        }
    }
}