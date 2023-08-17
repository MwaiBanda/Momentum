package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class VolunteeredMeal(
    val id: String,
    @SerialName("created_on")
    val createdOn: String,
    val description: String,
    val date: String,
    val notes: String,
    val user: User
)
