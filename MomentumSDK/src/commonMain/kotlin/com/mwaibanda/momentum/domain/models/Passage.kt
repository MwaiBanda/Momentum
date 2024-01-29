package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Passage(
    val id: String,
    val header: String?,
    val verse: String?,
    val message: String?,
    val notes: List<Note>?
)
