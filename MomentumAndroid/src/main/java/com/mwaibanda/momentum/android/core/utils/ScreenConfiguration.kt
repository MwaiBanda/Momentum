package com.mwaibanda.momentum.android.core.utils

import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.*

sealed class ScreenConfiguration(val screens: List<String>){
    object ScreensWithoutBackButton: ScreenConfiguration(
        listOf(
            PaymentSuccessScreen.route,
            PaymentFailureScreen.route,
            OfferScreen.route,
            LaunchScreen.route
        )
    )
    object ScreensWithoutNavigation: ScreenConfiguration(
        listOf(LaunchScreen.route)
    )
    object ScreensWithLogo: ScreenConfiguration(
        listOf(
            OfferScreen.route
        )
    )

}
