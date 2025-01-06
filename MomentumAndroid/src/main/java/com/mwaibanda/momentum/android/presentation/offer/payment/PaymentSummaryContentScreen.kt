package com.mwaibanda.momentum.android.presentation.offer.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mwaibanda.momentum.android.presentation.components.ToggleAmountLabel
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryContentViewModel.ToggleLabel.MISSIONS
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryContentViewModel.ToggleLabel.OFFERING
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryContentViewModel.ToggleLabel.OTHER
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryContentViewModel.ToggleLabel.SPECIAL_SPEAKER
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryContentViewModel.ToggleLabel.TITHE
import com.mwaibanda.momentum.utils.MultiplatformConstants

@Composable
fun PaymentSummaryContentScreen(
    otherLabel: String,
    amount: Int,
    contentViewModel: PaymentSummaryContentViewModel
) {
    LaunchedEffect(key1 = Unit){
        contentViewModel.totalAmount = amount.toString()
    }

    Column {
        ToggleAmountLabel(
            title = MultiplatformConstants.OFFERING,
            amount = contentViewModel.offeringAmount,
            isSelected = contentViewModel.offeringIsSelected,
            isDisabled = {
                (contentViewModel.selectedLabels.count() == 2).and(
                    contentViewModel.selectedLabels.contains(OFFERING).not()
                )
            },
            showLabels = contentViewModel.selectedLabels.isNotEmpty(),
            onToggleClick = {
                contentViewModel.offeringIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.otherIsSelected, type = OFFERING)
            },
            onAmountChange = {
                contentViewModel.offeringAmount = it
            },
            onAmountCommit = {
                contentViewModel.processAmount(amount = it, type = OFFERING)
            }
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.TITHE,
            amount = contentViewModel.titheAmount,
            isSelected = contentViewModel.titheIsSelected,
            isDisabled = {
                (contentViewModel.selectedLabels.count() == 2).and(
                    contentViewModel.selectedLabels.contains(TITHE).not()
                )
            },
            showLabels = contentViewModel.selectedLabels.isNotEmpty(),
            onToggleClick = {
                contentViewModel.titheIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.titheIsSelected, type = TITHE)
            },
            onAmountChange = {
                contentViewModel.titheAmount = it
            },
            onAmountCommit = {
                contentViewModel.processAmount(amount = it, type = TITHE)
            }
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.MISSIONS,
            amount = contentViewModel.missionsAmount,
            isSelected = contentViewModel.missionsIsSelected,
            isDisabled = {
                (contentViewModel.selectedLabels.count() == 2).and(
                    contentViewModel.selectedLabels.contains(MISSIONS).not()
                )
            },
            showLabels = contentViewModel.selectedLabels.isNotEmpty(),
            onToggleClick = {
                contentViewModel.missionsIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.missionsIsSelected, type = MISSIONS)

            },
            onAmountChange = {
                contentViewModel.missionsAmount = it
            },
            onAmountCommit = {
                contentViewModel.processAmount(amount = it, type = MISSIONS)
            }
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.SPECIAL_SPEAKER,
            amount = contentViewModel.speakersAmount,
            isSelected = contentViewModel.speakersIsSelected,
            isDisabled = {
                (contentViewModel.selectedLabels.count() == 2).and(
                    contentViewModel.selectedLabels.contains(SPECIAL_SPEAKER).not()
                )
            },
            showLabels = contentViewModel.selectedLabels.isNotEmpty(),
            onToggleClick = {
                contentViewModel.speakersIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.speakersIsSelected, type = SPECIAL_SPEAKER)
            },
            onAmountChange = {
                contentViewModel.speakersAmount = it
            },
            onAmountCommit = {
                contentViewModel.processAmount(amount = it, type = SPECIAL_SPEAKER)
            }
        )
        Divider()
        ToggleAmountLabel(
            title = otherLabel.ifEmpty { MultiplatformConstants.OTHER },
            amount = contentViewModel.otherAmount,
            isSelected = contentViewModel.otherIsSelected,
            isDisabled = {
                (contentViewModel.selectedLabels.count() == 2).and(
                    contentViewModel.selectedLabels.contains(OTHER).not()
                )
            },
            showLabels = contentViewModel.selectedLabels.isNotEmpty(),
            onToggleClick = {
                contentViewModel.otherIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.otherIsSelected, type = OTHER)
            },
            onAmountChange = {
                contentViewModel.otherAmount = it
            },
            onAmountCommit = {
                contentViewModel.processAmount(amount = it, type = OTHER)
            }
        )
    }
}