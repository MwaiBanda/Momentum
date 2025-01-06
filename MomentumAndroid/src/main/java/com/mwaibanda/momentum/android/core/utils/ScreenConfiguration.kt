package com.mwaibanda.momentum.android.core.utils

import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.AuthControllerScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.EventScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.LaunchScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.MealScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.MessageDetailScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.MessagesScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.OfferScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.PaymentFailureScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.PaymentSuccessScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.PlayerScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.ProfileScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.SermonScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.TransactionsScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.VolunteerScreen
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes.VolunteerServiceScreen

sealed class ScreenConfiguration(val screens: List<String>){
    data object ScreensWithoutBackButton: ScreenConfiguration(
        listOf(
            PaymentSuccessScreen.route,
            PaymentFailureScreen.route,
            OfferScreen.route,
            LaunchScreen.route,
            TransactionsScreen.route,
            SermonScreen.route,
            VolunteerScreen.route,
            MessagesScreen.route,
            EventScreen.route
        )
    )

    data object VolunteerScreens: ScreenConfiguration(
        listOf(
            MealScreen.route,
            VolunteerServiceScreen.route
        )
    )

    data object ScreensWithoutTopBarIcons: ScreenConfiguration(
        listOf(
            TransactionsScreen.route,
            AuthControllerScreen.route,
            SermonScreen.route,
            VolunteerScreen.route,
            MessagesScreen.route,
            MessageDetailScreen.route,
            EventScreen.route
        )
    )
    data object ScreensWithoutNavigation: ScreenConfiguration(
        listOf(
            LaunchScreen.route,
            TransactionsScreen.route,
            AuthControllerScreen.route,
            PlayerScreen.route
        )
    )
    data object ScreensWithWhiteStatusBar: ScreenConfiguration (
        listOf(
            ProfileScreen.route,
            PaymentSuccessScreen.route,
            PaymentFailureScreen.route,
        )
    )

    data object ScreensWithTransparentBottomBar: ScreenConfiguration (
        listOf(
            OfferScreen.route
        )
    )
    data object ScreensWithLogo: ScreenConfiguration(
        listOf(
            OfferScreen.route
        )
    )



}
