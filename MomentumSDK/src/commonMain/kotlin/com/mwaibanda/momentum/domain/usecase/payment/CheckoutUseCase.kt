package com.mwaibanda.momentum.domain.usecase.payment

import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.domain.repository.PaymentRepository
import com.mwaibanda.momentum.utils.Result

class CheckoutUseCase(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(request: Payment, onCompletion: (Result<PaymentResponse>) -> Unit){
         onCompletion(paymentRepository.prepareCheckout(request))
    }
}