package com.mwaibanda.momentum.data.sermonDTO

import kotlinx.serialization.Serializable

@Serializable
data class SeriesDTO(
    val description: String?,
    val image: String?,
    val slug: String?,
    val title: String?
)