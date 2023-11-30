package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.EventResponse
import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.eventDTO.EventsDTO
import com.mwaibanda.momentum.domain.repository.EventRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class EventRepositoryImpl(
    private val httpClient: HttpClient,
    private val getItemUseCase: GetItemUseCase<EventResponse>,
    private val setItemUseCase: SetItemUseCase<EventResponse>,
): MomentumBase(), EventRepository {
    override suspend fun fetchAllEvents(): Result<EventResponse> {
        val cacheEvents = getItemUseCase(EVENTS_KEY).orEmpty()
        if (cacheEvents.isNotEmpty()) {
            return Result.Success(cacheEvents)
        }
        try {
            val events = httpClient.get {
                momentumAPI(EVENTS_ENDPOINT)
            }.body<EventsDTO>().collection.map { it.toEvent() }
            setItemUseCase(EVENTS_KEY, events)
        } catch (e: Exception) {
            return  Result.Failure(e.message.toString())
        }
        val newlyCacheEvents = getItemUseCase(EVENTS_KEY).orEmpty()
        return Result.Success(newlyCacheEvents)
    }

    companion object {
        const val EVENTS_KEY = "events"
    }
}