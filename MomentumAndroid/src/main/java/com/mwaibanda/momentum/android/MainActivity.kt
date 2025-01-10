package com.mwaibanda.momentum.android

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
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
import com.mwaibanda.momentum.android.core.utils.Modal.*
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.*
import com.mwaibanda.momentum.android.presentation.MomentumEntry
import com.mwaibanda.momentum.android.presentation.auth.AuthControllerScreen
import com.mwaibanda.momentum.android.presentation.event.EventScreen
import com.mwaibanda.momentum.android.presentation.message.MessageDetailScreen
import com.mwaibanda.momentum.android.presentation.message.MessagesScreen
import com.mwaibanda.momentum.android.presentation.navigation.LaunchScreen
import com.mwaibanda.momentum.android.presentation.offer.OfferScreen
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentFailureScreen
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSuccessScreen
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryScreen
import com.mwaibanda.momentum.android.presentation.offer.profile.ProfileScreen
import com.mwaibanda.momentum.android.presentation.offer.transaction.TransactionScreen
import com.mwaibanda.momentum.android.presentation.sermon.PlayerScreen
import com.mwaibanda.momentum.android.presentation.sermon.SermonScreen
import com.mwaibanda.momentum.android.presentation.volunteer.VolunteerService
import com.mwaibanda.momentum.android.presentation.volunteer.VolunteerServiceDetail
import com.mwaibanda.momentum.android.presentation.volunteer.VolunteerServices
import com.mwaibanda.momentum.android.presentation.volunteer.meal.MealScreen
import com.mwaibanda.momentum.android.presentation.volunteer.meal.MealsDetailScreen
import com.mwaibanda.momentum.android.presentation.volunteer.meal.modals.PostMealScreen
import com.mwaibanda.momentum.android.presentation.volunteer.meal.modals.PostVolunteerMealScreen
import com.mwaibanda.momentum.android.presentation.volunteer.meal.modals.PostVolunteerServiceDayScreen
import com.mwaibanda.momentum.android.presentation.volunteer.meal.modals.PostVolunteerServiceScreen
import com.mwaibanda.momentum.android.presentation.volunteer.meal.modals.ViewRecipientInfoScreen
import com.mwaibanda.momentum.domain.models.Day
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.domain.models.VolunteerService
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.domain.models.value
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            MomentumApplication.DEFAULT_CHANNEL_ID,
            MomentumApplication.DEFAULT_CHANNEL,
            importance
        )
        channel.description = "This is the default app notification channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
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

            var currentVolunteeredMeal: VolunteeredMeal? by rememberSaveable {
                mutableStateOf(null)
            }

            var currentVolunteeredService: VolunteerService? by rememberSaveable {
                mutableStateOf(null)
            }
            var currentVolunteeredServiceDay: Day? by rememberSaveable {
                mutableStateOf(null)
            }

            var currentTab: Tab? by rememberSaveable {
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


            MomentumEntry(
                isShowingModal = showModalSheet,
                tab = currentTab,
                authViewModel = authViewModel,
                messageViewModel = messageViewModel,
                onShowModal = { showModal(it) }
            ) { contentPadding, navController ->
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
                            PostVolunteerService -> {
                                PostVolunteerServiceScreen(
                                    tab = currentTab,
                                    servicesViewModel = servicesViewModel,
                                    authViewModel = authViewModel,
                                    channel = mealChannel,
                                    closeModal
                                )
                            }
                            PostVolunteerServiceDay -> currentVolunteeredServiceDay?.let {
                                PostVolunteerServiceDayScreen(
                                    profileViewModel = profileViewModel,
                                    authViewModel = authViewModel,
                                    channel = dayChannel,
                                    volunteeredServiceDay = it,
                                    closeModal
                                )
                            }
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
                        composable(VolunteerScreen.route) {
                            VolunteerServices(
                                navController = navController,
                                servicesViewModel = servicesViewModel
                            ) { currentTab = it }
                        }
                        composable(
                            route = VolunteerServiceScreen.route,
                            arguments = listOf(navArgument("service") {
                                type = NavType.StringType
                            })
                        ) { navBackStackEntry ->
                            VolunteerService(
                                Json.decodeFromString(
                                    navBackStackEntry.arguments?.getString("service") ?: ""
                                ), servicesViewModel
                            ) {
                                currentVolunteeredService = it
                                navController.navigate("volunteer/${currentTab?.type?.value}/detail")
                            }
                        }
                        composable(
                            route = VolunteerServiceDetailScreen.route,
                            arguments = listOf(navArgument("service") {
                                type = NavType.StringType
                            })
                        ) {
                            currentVolunteeredService?.let {
                                VolunteerServiceDetail(
                                    currentService = it,
                                    channel = dayChannel,
                                    servicesViewModel = servicesViewModel
                                ) { modal, day ->
                                    currentVolunteeredServiceDay = day
                                    if (authViewModel.currentUser?.isGuest == true) {
                                        showModal(Authentication)
                                    } else {
                                        showModal(modal)
                                    }
                                }
                            }
                        }
                        composable(MealScreen.route) {
                            MealScreen(
                                mealViewModel = mealViewModel,
                                channel = mealChannel,
                                onMealSelected = {
                                    currentMeal = it
                                    navController.navigate(MealDetailScreen.route)
                                }
                            )
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
                            MessagesScreen(
                                navController = navController,
                                authViewModel = authViewModel,
                                messageViewModel = messageViewModel
                            ) {
                                messageViewModel.setMessage(it)
                            }
                        }
                        composable(MessageDetailScreen.route) {
                            MessageDetailScreen(
                                messageViewModel = messageViewModel,
                                authViewModel = authViewModel,
                            ) {
                                showModal(it)
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
