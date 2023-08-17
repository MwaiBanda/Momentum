package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result

interface PaymentRepository {
    suspend fun prepareCheckout(transaction: Payment): Result<PaymentResponse>
}