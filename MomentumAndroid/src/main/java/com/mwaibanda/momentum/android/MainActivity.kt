package com.mwaibanda.momentum.android

import android.Manifest
import android.app.PictureInPictureParams
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.dynamite.DynamiteModule
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.Modal
import com.mwaibanda.momentum.android.core.utils.Modal.Authentication
import com.mwaibanda.momentum.android.core.utils.Modal.PostMeal
import com.mwaibanda.momentum.android.core.utils.Modal.PostVolunteerMeal
import com.mwaibanda.momentum.android.core.utils.Modal.ViewRecipientInfo
import com.mwaibanda.momentum.android.core.utils.Modal.ViewTransactions
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.EventScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.LaunchScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.MealDetailScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.MealScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.MessageDetailScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.MessagesScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.OfferScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.PaymentFailureScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.PaymentSuccessScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.PaymentSummaryScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.PlayerScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.ProfileScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.SermonScreen
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import com.mwaibanda.momentum.android.presentation.auth.AuthControllerScreen
import com.mwaibanda.momentum.android.presentation.event.EventScreen
import com.mwaibanda.momentum.android.presentation.meal.MealScreen
import com.mwaibanda.momentum.android.presentation.meal.MealsDetailScreen
import com.mwaibanda.momentum.android.presentation.meal.modals.PostMealScreen
import com.mwaibanda.momentum.android.presentation.meal.modals.PostVolunteerMealScreen
import com.mwaibanda.momentum.android.presentation.meal.modals.ViewRecipientInfoScreen
import com.mwaibanda.momentum.android.presentation.message.MessageDetailScreen
import com.mwaibanda.momentum.android.presentation.message.MessagesScreen
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
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : BaseActivity() {
    private lateinit var castContext: CastContext
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        try {
            castContext = CastContext.getSharedInstance(this)
        } catch (e: RuntimeException) {
            var cause = e.cause
            while (cause != null) {
                if (cause is DynamiteModule.LoadingException) {
                    Log.e("CAST", "failed")
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
            var currentMessage: Message? by rememberSaveable {
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
                                transactionViewModel = transactionViewModel,
                                closeModal
                            )
                            Authentication -> AuthControllerScreen(
                                authViewModel = authViewModel,
                                profileViewModel = profileViewModel,
                                closeModal
                            )
                            PostMeal -> PostMealScreen(
                                mealViewModel = mealViewModel,
                                authViewModel = authViewModel,
                                channel = mealChannel,
                                closeModal
                            )
                            PostVolunteerMeal -> currentVolunteeredMeal?.let {
                                PostVolunteerMealScreen(
                                    profileViewModel = profileViewModel,
                                    authViewModel = authViewModel,
                                    channel = volunteeredMealChannel,
                                    volunteeredMeal = it,
                                    closeModal
                                )
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
                            MealScreen(
                                mealViewModel = mealViewModel,
                                channel = mealChannel,
                                onMealSelected = {
                                    currentMeal = it
                                    navController.navigate(MealDetailScreen.route)
                                }) {
                                if (authViewModel.currentUser?.isGuest == true) {
                                    showModal(Authentication)
                                } else {
                                    showModal(PostMeal)
                                }
                            }
                        }
                        composable(EventScreen.route) {
                            EventScreen()
                        }
                        composable(MealDetailScreen.route) {
                            currentMeal?.let { meal ->
                                MealsDetailScreen(
                                    mealViewModel = mealViewModel,
                                    channel = volunteeredMealChannel,
                                    currentMeal = meal
                                ) { modal, volunteeredMeal ->
                                    currentVolunteeredMeal = volunteeredMeal
                                    if (authViewModel.currentUser?.isGuest == true) {
                                        showModal(Authentication)
                                    } else {
                                        showModal(modal)
                                    }
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
                        composable(MessagesScreen.route) {
                            MessagesScreen(navController = navController, authViewModel = authViewModel, messageViewModel = messageViewModel) {
                                currentMessage = it
                            }
                        }
                        composable(MessageDetailScreen.route) {
                            currentMessage?.let {
                                MessageDetailScreen(
                                    message = it,
                                    messageViewModel = messageViewModel,
                                    authViewModel = authViewModel,
                                ) {
                                    showModal(it)
                                }
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
                        ) { navBackStackEntry ->
                            PaymentSummaryScreen(
                                navController = navController,
                                authViewModel = authViewModel,
                                profileViewModel = profileViewModel,
                                transactionViewModel = transactionViewModel,
                                amount = navBackStackEntry.arguments?.getFloat("amount") ?: 0.0f,
                                canInitiateTransaction = paymentViewModel.canInitiateTransaction,
                                onTransactionCanProcess = { canInitiateTransaction ->
                                    paymentViewModel.canInitiateTransaction = canInitiateTransaction
                                },
                                onShowModal = { showModalSheet = it },
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
