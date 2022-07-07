package com.mwaibanda.momentum.android.core.utils

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoutes(
    val route: String,
    @StringRes val tabName: Int? = null,
    val icon: ImageVector? = null,
) {
    object OfferScreen: NavigationRoutes(route = "offer")
    object PaymentSummaryScreen: NavigationRoutes(route = "pay/{amount}")

}
