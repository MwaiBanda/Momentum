package com.mwaibanda.momentum.android.presentation.payment

import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.DataResponse

class FakePaymentController: PaymentController {
    override fun checkout(request: Payment, onCompletion: (DataResponse<PaymentResponse>) -> Unit) {
        onCompletion(DataResponse.Success(PaymentResponse(
            publishableKey = "1001-001",
            paymentIntent = "Intent",
            customer = request.fullname,
            ephemeralKey = "10001"
        )))
    }
}