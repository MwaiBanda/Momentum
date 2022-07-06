package com.mwaibanda.momentum.android.presentation.offer.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse

class PaymentViewModel(
    private val paymentController: PaymentController
) {
    private val _paymentResponse: MutableLiveData<PaymentResponse> = MutableLiveData()
    val  paymentResponse: LiveData<PaymentResponse> = _paymentResponse

    fun checkout(paymentRequest: PaymentRequest){
        paymentController.checkout(paymentRequest){
            _paymentResponse.value = it
        }
    }
}