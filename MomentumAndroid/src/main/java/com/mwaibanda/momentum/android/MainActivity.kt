package com.mwaibanda.momentum.android

import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.*
import com.mwaibanda.momentum.android.presentation.auth.AuthControllerScreen
import com.mwaibanda.momentum.android.presentation.navigation.LaunchScreen
import com.mwaibanda.momentum.android.presentation.offer.OfferScreen
import com.mwaibanda.momentum.android.presentation.payment.PaymentFailureScreen
import com.mwaibanda.momentum.android.presentation.payment.PaymentSuccessScreen
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryScreen
import com.mwaibanda.momentum.android.presentation.profile.ProfileScreen
import com.mwaibanda.momentum.android.presentation.sermon.PlayerScreen
import com.mwaibanda.momentum.android.presentation.sermon.SermonScreen
import com.mwaibanda.momentum.android.presentation.transaction.TransactionScreen
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract

@ExperimentalMaterialNavigationApi
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                MomentumEntry { contentPadding, navController, bottomSheetNav ->
                    ModalBottomSheetLayout(bottomSheetNav) {
                        NavHost(
                            navController = navController,
                            modifier = Modifier.padding(contentPadding),
                            startDestination = LaunchScreen.route
                        ) {
                            composable(LaunchScreen.route) {
                                LaunchScreen(navController = navController)
                            }
                            composable(OfferScreen.route) {
                                OfferScreen(
                                    navController = navController,
                                    authViewModel = authViewModel
                                )
                            }
                            composable(SermonScreen.route) {
                                SermonScreen(
                                    navController = navController,
                                    sermonViewModel = sermonViewModel
                                )
                            }
                            composable(
                                route = PlayerScreen.route,
                                arguments = listOf(navArgument("videoURL") {
                                    type = NavType.StringType
                                })
                            ) {
                                PlayerScreen(
                                    navController = navController,
                                    sermonViewModel = sermonViewModel,
                                    showControls = showControls,
                                    videoURL = it.arguments?.getString("videoURL") ?: "",
                                    onShowControls =  { show -> showControls = show }
                                ){ bounds ->
                                    videoBounds = bounds
                                }
                            }
                            composable(ProfileScreen.route) {
                                ProfileScreen(
                                    navController = navController,
                                    authViewModel = authViewModel,
                                    profileViewModel = profileViewModel
                                )
                            }
                            composable(PaymentSuccessScreen.route) {
                                PaymentSuccessScreen(navController = navController)
                            }
                            composable(PaymentFailureScreen.route) {
                                PaymentFailureScreen(navController = navController)
                            }
                            composable(
                                route = PaymentSummaryScreen.route,
                                arguments = listOf(navArgument("amount") {
                                    type = NavType.FloatType
                                })
                            ) {
                                PaymentSummaryScreen(
                                    navController = navController,
                                    authViewModel = authViewModel,
                                    profileViewModel = profileViewModel,
                                    transactionViewModel = transactionViewModel,
                                    amount = it.arguments?.getFloat("amount") ?: 0.0f,
                                    canInitiateTransaction = paymentViewModel.canInitiateTransaction,
                                    onTransactionCanProcess = {
                                        paymentViewModel.canInitiateTransaction = it
                                    },
                                    onHandlePaymentSheetResult = ::onHandlePaymentResult
                                ) { request, launcher ->
                                    checkout(request) { customer, intent ->
                                        val configuration = PaymentSheet.Configuration(
                                            merchantDisplayName = MultiplatformConstants.MERCHANT_NAME,
                                            customer = customer,
                                            googlePay = googlePayConfig,
                                            allowsDelayedPaymentMethods = false,
                                            primaryButtonColor = ColorStateList.valueOf(
                                                Color(
                                                    C.MOMENTUM_ORANGE
                                                ).hashCode()
                                            )
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
                            bottomSheet(TransactionsScreen.route) {
                                TransactionScreen(
                                    authViewModel = authViewModel,
                                    transactionViewModel = transactionViewModel
                                ) {
                                    navController.popBackStack()
                                }
                            }
                            bottomSheet(AuthControllerScreen.route) {
                                AuthControllerScreen(
                                    authViewModel = authViewModel,
                                    profileViewModel = profileViewModel
                                ) {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        sermonViewModel.setLandscape(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        Log.d("Config" , "Layout is landscape: ${newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE}")
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            enterPictureInPictureMode(
                PictureInPictureParams.Builder()
                .setSourceRectHint(videoBounds)
                .setAspectRatio(Rational(16, 9))
                .build()
            )
            showControls = false
        }
    }
}
