package com.mwaibanda.momentum.android.presentation.payment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

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
    var missionAmount by mutableStateOf("0")
    var speakersAmount by mutableStateOf("0")
    var otherAmount by mutableStateOf("0")

    fun processToggle(isActive: Boolean, type: ToggleLabel){
        if (selectedLabels.contains(type)) {
            val index = selectedLabels.indexOf(type)
            if (!isActive) {
                selectedLabels.removeAt(index)
            }
        } else {
            selectedLabels.add(type)
        }
    }
}