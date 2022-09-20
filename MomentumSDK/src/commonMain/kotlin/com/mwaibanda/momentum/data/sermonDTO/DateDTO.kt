package com.mwaibanda.momentum.data.sermonDTO

import kotlinx.serialization.Serializable

@Serializable
data class DateDTO(
    val carbon: String,
    val date: String
)