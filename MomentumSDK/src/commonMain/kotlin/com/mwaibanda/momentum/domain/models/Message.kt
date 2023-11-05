package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    val thumbnail: String,
    val series: String,
    val title: String,
    val preacher: String,
    val date: String,
    val createdOn: String,
    val passages: List<Passage>
)