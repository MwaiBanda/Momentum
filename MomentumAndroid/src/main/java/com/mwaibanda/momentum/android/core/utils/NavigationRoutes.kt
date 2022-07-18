package com.mwaibanda.momentum.android.core.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.ui.graphics.vector.ImageVector
import com.mwaibanda.momentum.android.R

sealed class NavigationRoutes(
    val route: String,
    @StringRes val tabName: Int? = null,
    val icon: ImageVector? = null,
) {
    object LaunchScreen: NavigationRoutes(route = "launch")
    object OfferScreen: NavigationRoutes(route = "offer", tabName = R.string.offer, icon = Icons.Filled.CardGiftcard)
    object PaymentSummaryScreen: NavigationRoutes(route = "pay/{amount}")
    object PaymentSuccessScreen: NavigationRoutes(route = "pay/success")
    object PaymentFailureScreen: NavigationRoutes(route = "pay/failure")
}
