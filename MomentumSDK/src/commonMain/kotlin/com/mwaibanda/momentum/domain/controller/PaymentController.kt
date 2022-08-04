package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result

interface PaymentController {
    fun checkout(request: PaymentRequest, onCompletion: (Result<PaymentResponse>) -> Unit)
}