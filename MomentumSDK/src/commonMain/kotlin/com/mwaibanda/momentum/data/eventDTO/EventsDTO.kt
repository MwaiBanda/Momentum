package com.mwaibanda.momentum.data.eventDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsDTO (
    @SerialName("data")
    val collection: List<EventDTO>
)