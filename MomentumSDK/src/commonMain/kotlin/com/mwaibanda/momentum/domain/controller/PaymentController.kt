package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse

interface PaymentController {
    fun checkout(request: PaymentRequest, onCompletion: (PaymentResponse) -> Unit)
}