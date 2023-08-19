package com.mwaibanda.momentum.android

import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.dynamite.DynamiteModule
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.*
import com.mwaibanda.momentum.android.presentation.MomentumEntry
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
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

enum class Modal {
    Transactions,
    Auth
}
class MainActivity : BaseActivity() {
    private lateinit var castContext: CastContext

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            castContext = CastContext.getSharedInstance(this)
        } catch (e: RuntimeException) {
            var cause = e.cause
            while (cause != null) {
                if (cause is DynamiteModule.LoadingException) {
                    Log.e("CAST", "falied")
                    return
                }
                cause = cause.cause
            }
            throw e
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true,
            )
            val scope = rememberCoroutineScope()
            var showModalSheet by rememberSaveable {
                mutableStateOf(false)
            }
            var currentModal: Modal by rememberSaveable {
                mutableStateOf(Modal.Transactions)
            }
            LaunchedEffect(key1 = sheetState.isVisible, block = {
                launch {
                    showModalSheet = sheetState.isVisible
                }
            })
            var currentSermon: Sermon? by rememberSaveable {
                mutableStateOf(null)
            }
            MomentumEntry(showModalSheet, {
                scope.launch {
                    currentModal = Modal.Transactions
                    showModalSheet = true
                    sheetState.show()
                }
            }) { contentPadding, navController ->
                ModalBottomSheetLayout(
                    sheetState = sheetState,
                    sheetContent = {
                            when(currentModal){
                                Modal.Transactions -> TransactionScreen(
                                    authViewModel = authViewModel,
                                    transactionViewModel = transactionViewModel
                                ) {
                                    scope.launch {
                                        sheetState.hide()
                                        showModalSheet = false
                                    }
                                }
                                Modal.Auth -> AuthControllerScreen(
                                    authViewModel = authViewModel,
                                    profileViewModel = profileViewModel
                                ) {
                                    scope.launch {
                                        sheetState.hide()
                                        showModalSheet = false
                                    }
                                }
                            }

                    }
                ){
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
                            ){
                                scope.launch {
                                    currentModal = Modal.Auth
                                    showModalSheet = true
                                    sheetState.show()
                                }
                            }
                        }
                        composable(SermonScreen.route) {
                            SermonScreen(
                                navController = navController,
                                sermonViewModel = sermonViewModel
                            ) {
                                currentSermon = it
                                navController.navigate(PlayerScreen.route)
                            }
                        }
                        composable(
                            route = PlayerScreen.route
                        ) {
                            currentSermon?.let { sermon ->
                                PlayerScreen(
                                    castContext = castContext,
                                    navController = navController,
                                    sermonViewModel = sermonViewModel,
                                    showControls = showControls,
                                    sermon = sermon,
                                    onShowControls = { show -> showControls = show },
                                    canEnterPictureInPicture = { canEnterPictureInPicture = it }
                                ) { bounds ->
                                    videoBounds = bounds
                                }
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
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE) && canEnterPictureInPicture) {
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
