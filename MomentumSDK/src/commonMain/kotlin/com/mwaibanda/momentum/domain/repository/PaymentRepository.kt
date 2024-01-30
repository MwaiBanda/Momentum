package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.DataResponse

interface PaymentRepository {
    suspend fun prepareCheckout(transaction: Payment): DataResponse<PaymentResponse>
}