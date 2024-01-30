package com.mwaibanda.momentum.domain.usecase.event

import com.mwaibanda.momentum.domain.models.GroupedEvent
import com.mwaibanda.momentum.domain.repository.EventRepository
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.getFormattedDate

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(onCompletion: (DataResponse<List<GroupedEvent>>) -> Unit) {
        when(val eventsResponse = eventRepository.fetchAllEvents()) {
            is DataResponse.Failure -> {
                println("[GetEventsUseCase] ${eventsResponse.message ?: "Unknown Error"}")
                onCompletion(DataResponse.Failure(eventsResponse.message ?: "Unknown Error"))
            }
            is DataResponse.Success -> {
                val events = eventsResponse.data ?: emptyList()
                onCompletion(DataResponse.Success(
                    events
                        .groupBy { getFormattedDate(it.startTime, "MMMM YYYY") }
                        .map { GroupedEvent(it.key, it.value) }
                ))
            }
        }
    }
}