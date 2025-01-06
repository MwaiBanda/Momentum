package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VolunteerService(
    val days: List<Day>,
    val description: String,
    val email: String,
    val id: String,
    val organizer: String,
    val phone: String,
    @SerialName("service_id")
    val serviceId: String,
    @SerialName("user_id")
    val userId: String
)