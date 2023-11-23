package com.mwaibanda.momentum.domain.models

data class GroupedEvent(
    val monthAndYear: String,
    val events: List<Event>
)
