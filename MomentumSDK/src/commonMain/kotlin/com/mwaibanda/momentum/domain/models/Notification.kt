package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val title: String,
    val body: String,
    val topic: String
)
