package com.mwaibanda.momentum.data.eventDTO

import com.mwaibanda.momentum.domain.models.Event

data class EventDTO(
    val attributes: EventAttributesDTO,
    val id: String
) {
    fun toEvent() = Event(
        id = id,
        startTime = attributes.starts_at,
        endTime = attributes.ends_at,
        description = attributes.open_graph_description,
        intervalSummary = attributes.recurrence_description,
        name = attributes.name,
        thumbnail = attributes.image_url
    )
}