package com.mwaibanda.momentum.data.messageDTO

import com.mwaibanda.momentum.domain.models.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val data: List<Message>
)