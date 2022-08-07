package com.mwaibanda.momentum.android.core.utils

import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.*

sealed class ScreenConfiguration(val screens: List<String>){
    object ScreensWithoutBackButton: ScreenConfiguration(
        listOf(
            PaymentSuccessScreen.route,
            PaymentFailureScreen.route,
            OfferScreen.route,
            LaunchScreen.route,
            TransactionsScreen.route
        )
    )
    object ScreensWithTopBarIcons: ScreenConfiguration(
        listOf(
            TransactionsScreen.route,
            AuthControllerScreen.route
        )
    )
    object ScreensWithoutNavigation: ScreenConfiguration(
        listOf(
            LaunchScreen.route,
            TransactionsScreen.route,
            AuthControllerScreen.route
        )
    )
    object ScreensWithWhiteStatusBar: ScreenConfiguration (
        listOf(
            ProfileScreen.route,
            PaymentSuccessScreen.route,
            PaymentFailureScreen.route,
        )
    )
    object ScreensWithLogo: ScreenConfiguration(
        listOf(
            OfferScreen.route
        )
    )

}
