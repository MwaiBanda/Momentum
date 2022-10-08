package com.mwaibanda.momentum.android

import androidx.activity.ComponentActivity
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.payment.PaymentViewModel
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel
import com.mwaibanda.momentum.android.presentation.sermon.SermonViewModel
import com.mwaibanda.momentum.android.presentation.transaction.TransactionViewModel
import com.mwaibanda.momentum.domain.models.Transaction
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject

open class BaseActivity : ComponentActivity() {
    protected val authViewModel: AuthViewModel by inject()
    protected val paymentViewModel: PaymentViewModel by inject()
    protected val profileViewModel: ProfileViewModel by inject()
    protected val transactionViewModel: TransactionViewModel by inject()
    protected val sermonViewModel: SermonViewModel by inject()

    private val coroutineScope = MainScope()

    init {
        authViewModel.checkAndSignIn()
    }

    protected fun checkout(
        transaction: Transaction,
        onSuccess: (customer: PaymentSheet.CustomerConfiguration?, intent: String) -> Unit
    ) = coroutineScope.launch {
        paymentViewModel.checkout(transaction)
        paymentViewModel.paymentResponse.collectLatest { paymentResponse ->
            if (paymentResponse != null && paymentViewModel.canInitiateTransaction) {
                paymentViewModel.canInitiateTransaction = false
                PaymentConfiguration.init(applicationContext, paymentResponse.publishableKey)
                val customerConfig = PaymentSheet.CustomerConfiguration(
                    paymentResponse.customer,
                    paymentResponse.ephemeralKey
                )
                onSuccess(customerConfig, paymentResponse.paymentIntent)
            }
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

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    companion object {
        val googlePayConfig = PaymentSheet.GooglePayConfiguration(
            environment = PaymentSheet.GooglePayConfiguration.Environment.Production,
            countryCode = "US"
        )
    }
}