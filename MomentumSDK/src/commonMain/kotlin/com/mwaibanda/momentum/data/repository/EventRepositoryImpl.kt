package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.eventDTO.EventsDTO
import com.mwaibanda.momentum.domain.models.Event
import com.mwaibanda.momentum.domain.repository.EventRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class EventRepositoryImpl(
    private val httpClient: HttpClient
): MomentumBase(), EventRepository {
    override suspend fun fetchAllEvents(): Result<List<Event>> {
        return try {
            val events = httpClient.get {
                momentumAPI(EVENTS_ENDPOINT)
            }.body<EventsDTO>().collection.map { it.toEvent() }
            Result.Success(events)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }
}