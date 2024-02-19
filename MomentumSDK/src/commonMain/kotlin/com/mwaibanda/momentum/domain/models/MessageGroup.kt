package com.mwaibanda.momentum.domain.models

data class MessageGroup(
    val series: String,
    val messages: List<Message>
)
