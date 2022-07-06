package com.mwaibanda.momentum.android

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import com.mwaibanda.momentum.android.presentation.offer.OfferScreen
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryScreen
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MomentumEntry {
                NavHost(
                    navController = navController,
                    modifier = Modifier.padding(it),
                    startDestination = "offer"
                ) {
                    composable("offer") {
                        OfferScreen(navController = navController, offerViewModel = viewModel())
                    }
                    composable("pay") {
                        PaymentSummaryScreen(
                            onHandlePaymentSheetResult = ::onHandlePaymentResult,
                            onInitiateCheckout = { request, launcher ->
                                checkout(request) { customer, intent ->
                                    var configuration = PaymentSheet.Configuration(
                                        merchantDisplayName = merchantName,
                                        customer = customer,
                                        googlePay = googlePayConfig,
                                        allowsDelayedPaymentMethods = false
                                    )
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                    launcher.launch(
                                        PaymentSheetContract.Args.createPaymentIntentArgs(
                                            intent,
                                            configuration
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
