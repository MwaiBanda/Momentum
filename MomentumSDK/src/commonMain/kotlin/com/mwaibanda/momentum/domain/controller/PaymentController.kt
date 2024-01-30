package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.DataResponse

interface PaymentController {
    fun checkout(request: Payment, onCompletion: (DataResponse<PaymentResponse>) -> Unit)
}