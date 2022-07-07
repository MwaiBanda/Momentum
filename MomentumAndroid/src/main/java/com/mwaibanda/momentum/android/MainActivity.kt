package com.mwaibanda.momentum.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mwaibanda.momentum.android.presentation.offer.OfferScreen
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryScreen
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract
import org.koin.androidx.viewmodel.ext.android.getViewModel

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
                        OfferScreen(navController = navController, offerViewModel = getViewModel())
                    }
                    composable(
                        route = "pay/{amount}",
                        arguments = listOf(navArgument("amount") {  type = NavType.FloatType })
                    ) {
                        PaymentSummaryScreen(
                            amount = it.arguments?.getFloat("amount") ?: 0.0f,
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
