package com.mwaibanda.momentum.android.presentation.payment

import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result

class FakePaymentController: PaymentController {
    override fun checkout(
        request: PaymentRequest,
        onCompletion: (Result<PaymentResponse>) -> Unit
    ) {
        onCompletion(Result.Success(PaymentResponse(
            publishableKey = "1001-001",
            paymentIntent = "Intent",
            customer = request.fullname,
            ephemeralKey = "10001"
        )))
    }
}