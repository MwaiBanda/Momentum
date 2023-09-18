package com.mwaibanda.momentum.android

import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
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
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.dynamite.DynamiteModule
import com.mwaibanda.momentum.android.core.utils.Modal
import com.mwaibanda.momentum.android.core.utils.Modal.*
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.*
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import com.mwaibanda.momentum.android.presentation.auth.AuthControllerScreen
import com.mwaibanda.momentum.android.presentation.meals.MealScreen
import com.mwaibanda.momentum.android.presentation.meals.MealsDetailScreen
import com.mwaibanda.momentum.android.presentation.meals.modals.PostMealScreen
import com.mwaibanda.momentum.android.presentation.meals.modals.PostVolunteerMealScreen
import com.mwaibanda.momentum.android.presentation.meals.modals.ViewRecipientInfoScreen
import com.mwaibanda.momentum.android.presentation.navigation.LaunchScreen
import com.mwaibanda.momentum.android.presentation.offer.OfferScreen
import com.mwaibanda.momentum.android.presentation.payment.PaymentFailureScreen
import com.mwaibanda.momentum.android.presentation.payment.PaymentSuccessScreen
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryScreen
import com.mwaibanda.momentum.android.presentation.profile.ProfileScreen
import com.mwaibanda.momentum.android.presentation.sermon.PlayerScreen
import com.mwaibanda.momentum.android.presentation.sermon.SermonScreen
import com.mwaibanda.momentum.android.presentation.transaction.TransactionScreen
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract
import kotlinx.coroutines.launch

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
            val coroutineScope = rememberCoroutineScope()
            val sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true,
            )
            var showModalSheet by rememberSaveable {
                mutableStateOf(false)
            }

            var currentModal: Modal by rememberSaveable {
                mutableStateOf(ViewTransactions)
            }

            var currentSermon: Sermon? by rememberSaveable {
                mutableStateOf(null)
            }

            var currentMeal: Meal? by rememberSaveable {
                mutableStateOf(null)
            }

            var currentVolunteeredMeal: VolunteeredMeal? by rememberSaveable {
                mutableStateOf(null)
            }

            val showModal: (Modal) -> Unit = {
                coroutineScope.launch {
                    currentModal = it
                    showModalSheet = true
                    sheetState.show()
                }
            }

            val closeModal: () -> Unit = {
                coroutineScope.launch {
                    sheetState.hide()
                    showModalSheet = false
                }
            }

            LaunchedEffect(key1 = sheetState.isVisible, block = {
                showModalSheet = sheetState.isVisible
            })

            MomentumEntry(showModalSheet, {
                showModal(ViewTransactions)
            }) { contentPadding, navController ->
                ModalBottomSheetLayout(
                    sheetState = sheetState,
                    sheetContent = {
                        when (currentModal) {
                            ViewTransactions -> TransactionScreen(
                                authViewModel = authViewModel,
                                transactionViewModel = transactionViewModel
                            ) {
                                closeModal()
                            }
                            Authentication -> AuthControllerScreen(
                                authViewModel = authViewModel,
                                profileViewModel = profileViewModel
                            ) {
                                closeModal()
                            }
                            PostMeal -> PostMealScreen(closeModal)
                            PostVolunteerMeal -> currentVolunteeredMeal?.let {
                                PostVolunteerMealScreen(it, closeModal)
                            }
                            ViewRecipientInfo -> ViewRecipientInfoScreen(currentMeal, closeModal)
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(contentPadding),
                        startDestination = LaunchScreen.route
                    ) {
                        composable(LaunchScreen.route) {
                            LaunchScreen(navController = navController)
                        }
                        composable(MealScreen.route) {
                            MealScreen(mealViewModel = mealViewModel, onMealSelected = {
                                currentMeal = it
                                navController.navigate(MealDetailScreen.route)
                            }) {
                                showModal(PostMeal)
                            }
                        }
                        composable(MealDetailScreen.route) {
                            currentMeal?.let { meal ->
                                MealsDetailScreen(meal = meal) { modal, volunteeredMeal ->
                                    currentVolunteeredMeal = volunteeredMeal
                                    showModal(modal)
                                }
                            }
                        }
                        composable(OfferScreen.route) {
                            OfferScreen(
                                navController = navController,
                                authViewModel = authViewModel
                            ) {
                                showModal(Authentication)
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
        Log.d(
            "Config",
            "Layout is landscape: ${newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE}"
        )
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
