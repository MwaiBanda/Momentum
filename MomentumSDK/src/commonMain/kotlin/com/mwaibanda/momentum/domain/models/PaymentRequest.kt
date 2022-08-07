package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    val fullname: String,
    val email: String,
    val phone: String,
    val amount: Int
)
