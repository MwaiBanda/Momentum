package com.mwaibanda.momentum.data.sermonDTO

import kotlinx.serialization.Serializable

@Serializable
data class LinkDTO(
    val active: Boolean,
    val label: String,
    val url: String?
)