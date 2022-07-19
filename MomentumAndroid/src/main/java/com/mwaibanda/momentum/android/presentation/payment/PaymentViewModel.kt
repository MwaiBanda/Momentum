package com.mwaibanda.momentum.android.presentation.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PaymentViewModel: ViewModel(), KoinComponent {
    private val paymentController: PaymentController by inject()

    private val _paymentResponse: MutableLiveData<PaymentResponse> = MutableLiveData()
    val  paymentResponse: LiveData<PaymentResponse> = _paymentResponse

    fun checkout(paymentRequest: PaymentRequest){
        paymentController.checkout(paymentRequest){
            _paymentResponse.value = it
        }
    }
}