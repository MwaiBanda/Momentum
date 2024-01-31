package com.mwaibanda.momentum.domain.models

data class EventGroup(
    val monthAndYear: String,
    val events: List<Event>
)
