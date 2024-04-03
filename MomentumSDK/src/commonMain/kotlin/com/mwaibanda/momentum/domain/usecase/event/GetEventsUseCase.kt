package com.mwaibanda.momentum.domain.usecase.event

import com.mwaibanda.momentum.domain.models.EventGroup
import com.mwaibanda.momentum.domain.repository.EventRepository
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.getFormattedDate
import com.mwaibanda.momentum.utils.randomUUID

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(onCompletion: (DataResponse<List<EventGroup>>) -> Unit) {
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
                        .map {
                            EventGroup(
                                id = randomUUID(),
                                monthAndYear = it.key,
                                events = it.value
                            )
                        }
                ))
            }
        }
    }
}