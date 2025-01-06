package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class DayRequest(
    val date: String
)