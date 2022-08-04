package com.mwaibanda.momentum.android.presentation.payment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.Result.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PaymentViewModel(
    private val paymentController: PaymentController
): ViewModel() {

    private val _paymentResponse: MutableLiveData<PaymentResponse> = MutableLiveData()
    val  paymentResponse: LiveData<PaymentResponse> = _paymentResponse

    var canInitiateTransaction by mutableStateOf(true)

    fun checkout(paymentRequest: PaymentRequest){
        paymentController.checkout(paymentRequest){ res ->
            when(res) {
                is Failure -> {
                    Log.d("Pay/Failure", res.message ?: "")
                }
                is Success -> {
                    _paymentResponse.value = res.data
                }
            }
        }
    }
}