package com.mwaibanda.momentum.android.presentation.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PaymentViewModel(
    private val paymentController: PaymentController
): ViewModel() {

    private val _paymentResponse: MutableLiveData<PaymentResponse> = MutableLiveData()
    val  paymentResponse: LiveData<PaymentResponse> = _paymentResponse

    var canInitiateTransaction by mutableStateOf(true)

    fun checkout(paymentRequest: PaymentRequest){
        paymentController.checkout(paymentRequest){
            _paymentResponse.value = it
        }
    }
}