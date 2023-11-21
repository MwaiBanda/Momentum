package com.mwaibanda.momentum.data.eventDTO

import kotlinx.serialization.SerialName

data class EventsDTO (
    @SerialName("data")
    val collection: List<EventDTO>
)