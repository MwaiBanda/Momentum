package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class PaymentResponse(
    val publishableKey: String,
    val paymentIntent: String,
    val customer: String,
    val ephemeralKey: String
)
