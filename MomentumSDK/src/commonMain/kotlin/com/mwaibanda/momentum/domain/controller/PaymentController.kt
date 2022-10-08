package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result

interface PaymentController {
    fun checkout(request: Transaction, onCompletion: (Result<PaymentResponse>) -> Unit)
}