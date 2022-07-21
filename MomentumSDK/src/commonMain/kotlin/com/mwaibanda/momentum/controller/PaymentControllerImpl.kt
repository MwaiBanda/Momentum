package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.domain.usecase.CheckoutUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PaymentControllerImpl(
    private val checkoutUseCase: CheckoutUseCase
): PaymentController {
    private val scope = MainScope()

    override fun checkout(request: PaymentRequest, onCompletion: (PaymentResponse) -> Unit) {
        scope.launch {
            checkoutUseCase(request) {
                onCompletion(it)
            }
        }
    }
}