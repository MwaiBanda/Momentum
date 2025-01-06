package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: String,
    val image: String,
    val name: String,
    val services: List<VolunteerService>,
    val type: String
)