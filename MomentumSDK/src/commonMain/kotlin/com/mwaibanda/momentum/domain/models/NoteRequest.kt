package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val content: String,
    val userId: String,
    val passageId: String,
)
