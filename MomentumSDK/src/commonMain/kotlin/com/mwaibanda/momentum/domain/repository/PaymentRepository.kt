package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result

interface PaymentRepository {
    suspend fun prepareCheckout(paymentRequest: PaymentRequest): Result<PaymentResponse>
}