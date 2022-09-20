package com.mwaibanda.momentum.data.sermonDTO

import kotlinx.serialization.Serializable

@Serializable
data class InteractionsDTO(
    val likes: Int,
    val shares: Int
)