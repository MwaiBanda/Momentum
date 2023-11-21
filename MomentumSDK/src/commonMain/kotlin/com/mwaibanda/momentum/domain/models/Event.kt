package com.mwaibanda.momentum.domain.models

data class Event(
    val id: String,
    val startTime: String,
    val endTime: String,
    val description: String,
    val intervalSummary: String,
    val name: String,
    val thumbnail: String
)
