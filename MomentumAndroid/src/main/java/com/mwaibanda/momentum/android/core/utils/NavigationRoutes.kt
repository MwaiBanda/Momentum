package com.mwaibanda.momentum.android.core.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.ui.graphics.vector.ImageVector
import com.mwaibanda.momentum.android.R

sealed class NavigationRoutes(
    val route: String,
    @StringRes val tabName: Int? = null,
    val icon: ImageVector? = null,
) {
    data object LaunchScreen: NavigationRoutes(route = "launch")

    data object MealScreen: NavigationRoutes(route = "meals", tabName = R.string.meals, icon = Icons.Filled.Fastfood)
    data object MealDetailScreen: NavigationRoutes(route = "meals/detail")

    data object OfferScreen: NavigationRoutes(route = "offer", tabName = R.string.offer, icon = Icons.Filled.CardGiftcard)
    data object MessagesScreen: NavigationRoutes(route = "messages", tabName = R.string.messages, icon = Icons.Filled.MenuBook)

    data object SermonScreen: NavigationRoutes(route = "sermon", tabName = R.string.sermon, icon = Icons.Filled.LocalMovies)
    data object PlayerScreen: NavigationRoutes(route = "play/sermon")

    data object PaymentSummaryScreen: NavigationRoutes(route = "pay/{amount}")
    data object PaymentSuccessScreen: NavigationRoutes(route = "pay/success")
    data object PaymentFailureScreen: NavigationRoutes(route = "pay/failure")
    data object TransactionsScreen: NavigationRoutes(route = "transactions")

    data object ProfileScreen: NavigationRoutes(route = "profile")
    data object AuthControllerScreen: NavigationRoutes(route = "auth")
}
