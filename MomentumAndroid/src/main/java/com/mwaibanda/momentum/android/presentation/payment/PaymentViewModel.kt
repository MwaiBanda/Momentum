package com.mwaibanda.momentum.android.presentation.payment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.Result.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PaymentViewModel(
    private val paymentController: PaymentController
): ViewModel() {

    private val _paymentResponse: MutableStateFlow<PaymentResponse?> = MutableStateFlow(null)
    val  paymentResponse: StateFlow<PaymentResponse?> = _paymentResponse.asStateFlow()

    var canInitiateTransaction by mutableStateOf(true)

    fun checkout(paymentRequest: PaymentRequest){
            paymentController.checkout(paymentRequest) { res ->
                when (res) {
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