package com.mwaibanda.momentum.android

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.*
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
                    startDestination = OfferScreen.route
                ) {
                    composable(OfferScreen.route) {
                        OfferScreen(navController = navController, offerViewModel = getViewModel())
                    }
                    composable(
                        route = PaymentSummaryScreen.route,
                        arguments = listOf(navArgument("amount") {  type = NavType.FloatType })
                    ) {
                        PaymentSummaryScreen(
                            amount = it.arguments?.getFloat("amount") ?: 0.0f,
                            onHandlePaymentSheetResult = ::onHandlePaymentResult
                        ) { request, launcher ->
                            checkout(request) { customer, intent ->
                                val configuration = PaymentSheet.Configuration(
                                    merchantDisplayName = merchantName,
                                    customer = customer,
                                    googlePay = googlePayConfig,
                                    allowsDelayedPaymentMethods = false,
                                    primaryButtonColor = ColorStateList.valueOf(Color(Constants.MomemtumOrange).hashCode())
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
                    }
                }
            }
        }
    }
}
