package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Passage(
    val header: String?,
    val verse: String?,
    val message: String?
)
