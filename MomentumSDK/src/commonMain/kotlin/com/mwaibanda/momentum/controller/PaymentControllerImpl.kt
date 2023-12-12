package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.domain.usecase.payment.CheckoutUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PaymentControllerImpl: PaymentController, KoinComponent {
    private val checkoutUseCase: CheckoutUseCase by inject()
    private val completableJob = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + completableJob)
    override fun checkout(request: Payment, onCompletion: (Result<PaymentResponse>) -> Unit) {
        scope.launch {
            checkoutUseCase(request, onCompletion)
        }
    }
}