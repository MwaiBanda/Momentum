package com.mwaibanda.momentum.android.presentation.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel.ToggleLabel.MISSIONS
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel.ToggleLabel.OFFERING
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel.ToggleLabel.OTHER
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel.ToggleLabel.SPECIAL_SPEAKER
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel.ToggleLabel.TITHE

class PaymentSummaryContentViewModel: ViewModel() {
    enum class ToggleLabel {
        OFFERING, TITHE, MISSIONS, SPECIAL_SPEAKER, OTHER
    }
    data class ToggleOption(val amount: Int, val type: ToggleLabel)
    var selectedLabels: MutableList<ToggleLabel> = mutableStateListOf()
    var previousOption: ToggleOption? by mutableStateOf(null)
    /* isSelected State */
    var offeringIsSelected by mutableStateOf(false)
    var titheIsSelected by mutableStateOf(false)
    var missionsIsSelected by mutableStateOf(false)
    var speakersIsSelected by mutableStateOf(false)
    var otherIsSelected by mutableStateOf(false)

    /* Amount State */
    var offeringAmount by mutableStateOf("0")
    var titheAmount by mutableStateOf("0")
    var missionsAmount by mutableStateOf("0")
    var speakersAmount by mutableStateOf("0")
    var otherAmount by mutableStateOf("0")
    var totalAmount by mutableStateOf("0")

    fun processToggle(isActive: Boolean, type: ToggleLabel){
        if (selectedLabels.contains(type)) {
            val index = selectedLabels.indexOf(type)
            if (!isActive) {
                selectedLabels.removeAt(index)
                resetPrevious()
                if (selectedLabels.isEmpty())
                    resetAmounts()
            }
        } else {
            selectedLabels.add(type)
            if (selectedLabels.count() == 1) {
                previousOption = ToggleOption(amount = 0, type = type)
                resetPrevious()
            }
        }
        if (selectedLabels.count() == 1) {
            resetAmounts()
            when (selectedLabels.first()) {
                OFFERING -> offeringAmount = totalAmount
                TITHE -> titheAmount = totalAmount
                MISSIONS -> missionsAmount = totalAmount
                SPECIAL_SPEAKER -> speakersAmount = totalAmount
                OTHER -> otherAmount = totalAmount
            }
        }
    }
    fun processAmount(amount: String, type: ToggleLabel){
        previousOption = ToggleOption(amount = amount.toInt(), type = type)
        if (selectedLabels.count() == 2) {
            when (selectedLabels[0]) {
                OFFERING -> {
                    when (selectedLabels[1]) {
                        OFFERING -> {}
                        TITHE -> {
                            if (previousOption?.type == TITHE)
                                setOffering(amount = amount)
                            else
                                setTithe(amount = amount)
                        }
                        MISSIONS -> {
                            if (previousOption?.type == MISSIONS)
                                setOffering(amount = amount)
                            else
                                setMissions(amount = amount)
                        }
                        SPECIAL_SPEAKER -> {
                            if (previousOption?.type == SPECIAL_SPEAKER)
                                setOffering(amount = amount)
                            else
                                setSpeakers(amount = amount)
                        }
                        OTHER -> {
                            if (previousOption?.type == OTHER)
                                setOffering(amount = amount)
                            else
                                setOther(amount = amount)
                        }
                    }
                }
                TITHE -> {
                    when (selectedLabels[1]) {
                        OFFERING -> {
                            if (previousOption?.type == OFFERING)
                                setTithe(amount = amount)
                            else
                                setOffering(amount = amount)
                        }
                        TITHE -> {}
                        MISSIONS -> {
                            if (previousOption?.type == MISSIONS)
                                setTithe(amount = amount)
                            else
                                setMissions(amount = amount)
                        }
                        SPECIAL_SPEAKER -> {
                            if (previousOption?.type == SPECIAL_SPEAKER)
                                setTithe(amount = amount)
                            else
                                setSpeakers(amount = amount)
                        }
                        OTHER -> {
                            if (previousOption?.type == OTHER)
                                setTithe(amount = amount)
                            else
                                setOther(amount = amount)
                        }
                    }
                }
                MISSIONS -> {
                    when (selectedLabels[1]) {
                        OFFERING ->  {
                            if (previousOption?.type == OFFERING)
                                setMissions(amount = amount)
                            else
                                setOffering(amount = amount)
                        }
                        TITHE -> {
                            if (previousOption?.type == TITHE)
                                setMissions(amount = amount)
                            else
                                setTithe(amount = amount)
                        }
                        MISSIONS -> {}
                        SPECIAL_SPEAKER -> {
                            if (previousOption?.type == SPECIAL_SPEAKER)
                                setMissions(amount = amount)
                            else
                                setSpeakers(amount = amount)
                        }
                        OTHER -> {
                            if (previousOption?.type == OTHER)
                                setMissions(amount = amount)
                            else
                                setOther(amount = amount)
                        }
                    }
                }
                SPECIAL_SPEAKER -> {
                    when (selectedLabels[1]) {
                        OFFERING -> {
                            if (previousOption?.type == OFFERING)
                                setSpeakers(amount = amount)
                            else
                                setOffering(amount = amount)
                        }
                        TITHE -> {
                            if (previousOption?.type == TITHE)
                                setSpeakers(amount = amount)
                            else
                                setTithe(amount = amount)
                        }
                        MISSIONS -> {
                            if (previousOption?.type == MISSIONS)
                                setSpeakers(amount = amount)
                            else
                                setMissions(amount = amount)
                        }
                        SPECIAL_SPEAKER -> {}
                        OTHER -> {
                            if (previousOption?.type == OTHER)
                                setSpeakers(amount = amount)
                            else
                                setOther(amount = amount)
                        }
                    }
                }
                OTHER -> {
                    when (selectedLabels[1]) {
                        OFFERING -> {
                            if (previousOption?.type == OFFERING)
                                setOther(amount = amount)
                            else
                                setOffering(amount = amount)
                        }
                        TITHE -> {
                            if (previousOption?.type == TITHE)
                                setOther(amount = amount)
                            else
                                setTithe(amount = amount)
                        }
                        MISSIONS -> {
                            if (previousOption?.type == MISSIONS)
                                setOther(amount = amount)
                            else
                                setMissions(amount = amount)
                        }
                        SPECIAL_SPEAKER -> {
                            if (previousOption?.type == SPECIAL_SPEAKER)
                                setOther(amount = amount)
                            else
                                setSpeakers(amount = amount)
                        }
                        OTHER -> {}
                    }
                }
            }
        }
    }
    fun  getTransactionDescription(otherLabel: String): String {
        if (selectedLabels.count() == 1) {
            return when(selectedLabels[0]) {
                OFFERING -> "$$offeringAmount: Offering"
                TITHE -> "$$titheAmount: Tithe"
                MISSIONS -> "$$missionsAmount: Missions"
                SPECIAL_SPEAKER -> "$$speakersAmount: Special Speaker"
                OTHER -> "$$otherAmount: ${otherLabel.ifEmpty { "Other" }}"
            }
        } else {
            var description = ""
            selectedLabels.forEach { toggleLabel ->
                if (selectedLabels.last() == toggleLabel) {
                      description += when(toggleLabel) {
                          OFFERING -> "$$offeringAmount: Offering"
                          TITHE -> "$$titheAmount: Tithe"
                          MISSIONS -> "$$missionsAmount: Missions"
                          SPECIAL_SPEAKER -> "$$speakersAmount: Special Speaker"
                          OTHER -> "$$otherAmount: ${otherLabel.ifEmpty { "Other" }}"
                      }
                } else {
                    description += when(toggleLabel) {
                        OFFERING -> "$$offeringAmount: Offering, "
                        TITHE -> "$$titheAmount: Tithe, "
                        MISSIONS -> "$$missionsAmount: Missions, "
                        SPECIAL_SPEAKER -> "$$speakersAmount: Special Speaker, "
                        OTHER -> "$$otherAmount: ${otherLabel.ifEmpty { "Other" }}"
                    }
                }
            }
            return description
        }
    }
    private fun subtractAmounts(vararg amounts: String): String {
        val reminder = amounts[0].toInt() - amounts[1].toInt()
        return reminder.toString()
    }

    private fun addAmounts(vararg amounts: String): String {
        val reminder = amounts[0].toInt() + amounts[1].toInt()
        return reminder.toString()
    }

    private fun setOther(amount: String) {
        if ((previousOption?.amount ?: 0) > amount.toInt()) {
            otherAmount = addAmounts(totalAmount, amount)
        } else {
            otherAmount = subtractAmounts(totalAmount, amount)
        }
    }

    private fun setOffering(amount: String) {
        if ((previousOption?.amount ?: 0) > amount.toInt()) {
            offeringAmount = addAmounts(totalAmount, amount)
        } else {
            offeringAmount = subtractAmounts(totalAmount, amount)
        }
    }

    private fun setTithe(amount: String) {
        if ((previousOption?.amount ?: 0) > amount.toInt()) {
            titheAmount = addAmounts(totalAmount, amount)
        } else {
            titheAmount = subtractAmounts(totalAmount, amount)
        }
    }

    private fun setMissions(amount: String) {
        if ((previousOption?.amount ?: 0) > amount.toInt()) {
            missionsAmount = addAmounts(totalAmount, amount)
        } else {
            missionsAmount = subtractAmounts(totalAmount, amount)
        }
    }

    private fun setSpeakers(amount: String) {
        if ((previousOption?.amount ?: 0) > amount.toInt()) {
            speakersAmount = addAmounts(totalAmount, amount)
        } else {
            speakersAmount = subtractAmounts(totalAmount, amount)
        }
    }

    private fun  resetPrevious(){
        when(previousOption?.type) {
            OFFERING -> offeringAmount = "0"
            TITHE -> titheAmount = "0"
            MISSIONS -> missionsAmount = "0"
            SPECIAL_SPEAKER -> speakersAmount = "0"
            OTHER -> otherAmount = "0"
            null -> {}
        }
    }

    private fun resetAmounts() {
        offeringAmount = "0"
        titheAmount = "0"
        missionsAmount = "0"
        speakersAmount = "0"
        otherAmount = "0"
    }
}