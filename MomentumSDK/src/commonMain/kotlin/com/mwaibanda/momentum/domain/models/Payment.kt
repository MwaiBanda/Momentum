package com.mwaibanda.momentum.domain.models

@kotlinx.serialization.Serializable
data class Payment(
    val amount: Int,
    val fullname: String,
    val email: String,
    val phone: String,
)
