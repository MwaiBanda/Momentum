package com.mwaibanda.momentum.payments

import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result

class FakePaymentController: PaymentController {
    
    override fun checkout(
        request: PaymentRequest,
        onCompletion: (Result<PaymentResponse>) -> Unit
    ) {
        val response = PaymentResponse(
            publishableKey=  "100101-111",
            paymentIntent = "Intent",
            customer = "Mwai Banda",
            ephemeralKey = "0007722"
        )
        onCompletion(Result.Success(response))
    }
}
