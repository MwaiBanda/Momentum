package com.mwaibanda.momentum.android.presentation.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import com.mwaibanda.momentum.android.presentation.components.ToggleAmountLabel
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel.ToggleLabel.*
import com.mwaibanda.momentum.utils.MultiplatformConstants
import org.koin.androidx.compose.getViewModel

@Composable
fun PaymentSummaryContentScreen(contentViewModel: PaymentSummaryContentViewModel = getViewModel()) {
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
            onToggleClick = {
                contentViewModel.offeringIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.otherIsSelected, type = OFFERING)
            },
            onAmountChange = {
                contentViewModel.offeringAmount = it
            },
            onAmountCommit = {}
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
            onToggleClick = {
                contentViewModel.titheIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.titheIsSelected, type = TITHE)
            },
            onAmountChange = {
                contentViewModel.titheAmount = it
            },
            onAmountCommit = {}
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.MISSIONS,
            amount = contentViewModel.missionAmount,
            isSelected = contentViewModel.missionsIsSelected,
            isDisabled = {
                (contentViewModel.selectedLabels.count() == 2).and(
                    contentViewModel.selectedLabels.contains(MISSIONS).not()
                )
            },
            onToggleClick = {
                contentViewModel.missionsIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.missionsIsSelected, type = MISSIONS)

            },
            onAmountChange = {
                contentViewModel.missionAmount = it
            },
            onAmountCommit = {}
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
            onToggleClick = {
                contentViewModel.speakersIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.speakersIsSelected, type = SPECIAL_SPEAKER)
            },
            onAmountChange = {
                contentViewModel.speakersAmount = it
            },
            onAmountCommit = {}
        )
        Divider()
        ToggleAmountLabel(
            title = MultiplatformConstants.OTHER,
            amount = contentViewModel.otherAmount,
            isSelected = contentViewModel.otherIsSelected,
            isDisabled = {
                (contentViewModel.selectedLabels.count() == 2).and(
                    contentViewModel.selectedLabels.contains(OTHER).not()
                )
            },
            onToggleClick = {
                contentViewModel.otherIsSelected = it.not()
                contentViewModel.processToggle(isActive = contentViewModel.otherIsSelected, type = OTHER)
            },
            onAmountChange = {
                contentViewModel.otherAmount = it
            },
            onAmountCommit = {}
        )
    }
}