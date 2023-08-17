package com.mwaibanda.momentum.domain.models

@kotlinx.serialization.Serializable
data class TransactionRequest(
    val id: String,
    val description: String,
    val amount: Int,
    val date: String,
    val userId: String
)
