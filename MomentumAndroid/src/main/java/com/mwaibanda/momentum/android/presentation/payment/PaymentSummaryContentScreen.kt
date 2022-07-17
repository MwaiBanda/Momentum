package com.mwaibanda.momentum.android.presentation.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import com.mwaibanda.momentum.android.presentation.components.ToggleAmountLabel
import com.mwaibanda.momentum.utils.MultiplatformConstants
import org.koin.androidx.compose.getViewModel

@Composable
fun PaymentSummaryContentScreen(contentViewModel: PaymentSummaryContentViewModel = getViewModel()) {
    Column {
        ToggleAmountLabel(
            title = MultiplatformConstants.OFFERING,
            amount = "$0",
            isSelected = true,
            onToggleClick = {},
            onAmountChange = {},
            onAmountCommit = {}
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.TITHE,
            amount = "$0",
            isSelected = false,
            onToggleClick = {},
            onAmountChange = {},
            onAmountCommit = {}
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.MISSIONS,
            amount = "$0",
            isSelected = false,
            onToggleClick = {},
            onAmountChange = {},
            onAmountCommit = {}
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.SPECIAL_SPEAKER,
            amount = "$0",
            isSelected = false,
            onToggleClick = {},
            onAmountChange = {},
            onAmountCommit = {}
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.OTHER,
            amount = "$0",
            isSelected = false,
            onToggleClick = {},
            onAmountChange = {},
            onAmountCommit = {}
        )
    }
}