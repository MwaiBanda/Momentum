package com.mwaibanda.momentum.android

import androidx.activity.ComponentActivity
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.payment.PaymentViewModel
import com.mwaibanda.momentum.android.presentation.profie.ProfileViewModel
import com.mwaibanda.momentum.android.presentation.transaction.TransactionViewModel
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

open class BaseActivity : ComponentActivity() {
    protected val authViewModel: AuthViewModel by inject()
    protected val paymentViewModel: PaymentViewModel by inject()
    protected val profileViewModel: ProfileViewModel by inject()
    protected val transactionViewModel: TransactionViewModel by inject()


    protected fun checkout(
        paymentRequest: PaymentRequest,
        onSuccess: (customer: PaymentSheet.CustomerConfiguration?, intent: String) -> Unit
    ) {
        paymentViewModel.checkout(paymentRequest)
        paymentViewModel.paymentResponse.observe(this) { paymentResponse ->
            PaymentConfiguration.init(this, paymentResponse.publishableKey)

            val customerConfig = PaymentSheet.CustomerConfiguration(
                paymentResponse.customer,
                paymentResponse.ephemeralKey
            )
            onSuccess(customerConfig, paymentResponse.paymentIntent)
            paymentViewModel.paymentResponse.removeObservers(this)
        }
    }

    protected fun onHandlePaymentResult(
        paymentResult: PaymentSheetResult,
        onPaymentSuccess: () -> Unit,
        onPaymentCancellation: () -> Unit,
        onPaymentFailure: (String) -> Unit
    ) {
        when (paymentResult) {
            is PaymentSheetResult.Completed -> onPaymentSuccess()
            is PaymentSheetResult.Canceled -> onPaymentCancellation()
            is PaymentSheetResult.Failed -> onPaymentFailure(paymentResult.error.localizedMessage!!)
        }
    }

    companion object {
        val googlePayConfig = PaymentSheet.GooglePayConfiguration(
            environment = PaymentSheet.GooglePayConfiguration.Environment.Production,
            countryCode = "US"
        )
    }
}