package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Day(
    val date: String,
    val id: String,
    val notes: String,
    val user: User
)