package com.mwaibanda.momentum.domain.models

data class ScheduledEvent(
    val monthAndYear: String,
    val events: List<Event>
)
