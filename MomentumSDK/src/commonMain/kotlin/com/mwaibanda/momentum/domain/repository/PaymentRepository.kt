package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse

interface PaymentRepository {
    suspend fun prepareCheckout(paymentRequest: PaymentRequest): PaymentResponse
}