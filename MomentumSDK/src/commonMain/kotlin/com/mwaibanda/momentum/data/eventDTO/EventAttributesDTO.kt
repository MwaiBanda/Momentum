package com.mwaibanda.momentum.data.eventDTO

data class EventAttributesDTO(
    val all_day_event: Boolean,
    val ends_at: String,
    val image_url: String,
    val name: String,
    val open_graph_description: String,
    val recurrence_description: String,
    val recurring: Boolean,
    val starts_at: String
)