package com.mwaibanda.momentum.android.core.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.ui.graphics.vector.ImageVector
import com.mwaibanda.momentum.android.R

sealed class NavigationRoutes(
    val route: String,
    @StringRes val tabName: Int? = null,
    val icon: ImageVector? = null,
) {
    object LaunchScreen: NavigationRoutes(route = "launch")
    object MealsScreen: NavigationRoutes(route = "offer", tabName = R.string.offer, icon = Icons.Filled.CardGiftcard)
    object MealScreen: NavigationRoutes(route = "meals", tabName = R.string.meals, icon = Icons.Filled.Fastfood)

    object OfferScreen: NavigationRoutes(route = "offer", tabName = R.string.offer, icon = Icons.Filled.CardGiftcard)
    object SermonScreen: NavigationRoutes(route = "sermon", tabName = R.string.sermon, icon = Icons.Filled.LocalMovies)
    object PlayerScreen: NavigationRoutes(route = "play/sermon")
    object PaymentSummaryScreen: NavigationRoutes(route = "pay/{amount}")
    object PaymentSuccessScreen: NavigationRoutes(route = "pay/success")
    object PaymentFailureScreen: NavigationRoutes(route = "pay/failure")
    object TransactionsScreen: NavigationRoutes(route = "transactions")
    object ProfileScreen: NavigationRoutes(route = "profile")
    object AuthControllerScreen: NavigationRoutes(route = "auth")
}
