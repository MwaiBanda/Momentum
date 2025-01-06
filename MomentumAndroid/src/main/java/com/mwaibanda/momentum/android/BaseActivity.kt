package com.mwaibanda.momentum.android

import android.graphics.Rect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentActivity
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.message.MessageViewModel
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentViewModel
import com.mwaibanda.momentum.android.presentation.offer.profile.ProfileViewModel
import com.mwaibanda.momentum.android.presentation.offer.transaction.TransactionViewModel
import com.mwaibanda.momentum.android.presentation.sermon.SermonViewModel
import com.mwaibanda.momentum.android.presentation.volunteer.ServicesViewModel
import com.mwaibanda.momentum.android.presentation.volunteer.meal.MealViewModel
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

open class BaseActivity : FragmentActivity() {
    protected val mealViewModel: MealViewModel by inject()
    protected val servicesViewModel: ServicesViewModel by inject()
    protected val authViewModel: AuthViewModel by inject()
    protected val paymentViewModel: PaymentViewModel by inject()
    protected val profileViewModel: ProfileViewModel by inject()
    protected val transactionViewModel: TransactionViewModel by inject()
    protected val sermonViewModel: SermonViewModel by inject()
    protected val messageViewModel: MessageViewModel by inject()
    protected var videoBounds = Rect()
    protected var showControls by mutableStateOf(true)
    protected var canEnterPictureInPicture by mutableStateOf(false)

    private val coroutineScope = MainScope()

    protected val volunteeredMealChannel = Channel<VolunteeredMeal>()
    protected val mealChannel = Channel<Meal>()
    protected val tabChannel = Channel<Tab>()

    init {
        authViewModel.checkAndSignIn()
    }

    protected fun checkout(
        transaction: Payment,
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
            countryCode = "US",
            currencyCode = "USD"
        )
    }
}