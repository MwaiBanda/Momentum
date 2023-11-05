package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    val data: List<Message>
)