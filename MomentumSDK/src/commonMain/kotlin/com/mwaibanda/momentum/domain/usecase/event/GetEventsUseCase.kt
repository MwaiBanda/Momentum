package com.mwaibanda.momentum.domain.usecase.event

import com.mwaibanda.momentum.domain.models.GroupedEvent
import com.mwaibanda.momentum.domain.repository.EventRepository
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.getFormattedDate

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(onCompletion: (Result<List<GroupedEvent>>) -> Unit) {
        when(val eventsResponse = eventRepository.fetchAllEvents()) {
            is Result.Failure -> {
                println("[GetEventsUseCase] ${eventsResponse.message ?: "Unknown Error"}")
                onCompletion(Result.Failure(eventsResponse.message ?: "Unknown Error"))
            }
            is Result.Success -> {
                val events = eventsResponse.data ?: emptyList()
                onCompletion(Result.Success(
                    events
                        .groupBy { getFormattedDate(it.startTime, "MMMM YYYY") }
                        .map { GroupedEvent(it.key, it.value) }
                ))
            }
        }
    }
}