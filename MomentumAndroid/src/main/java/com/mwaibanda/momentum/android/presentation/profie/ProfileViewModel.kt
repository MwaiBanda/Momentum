package com.mwaibanda.momentum.android.presentation.profie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.android.presentation.profie.ProfileViewModel.ProfileCard.*

class ProfileViewModel: ViewModel() {
    enum class ProfileCard {
        CONTACT_INFO,
        BILLING_INFO,
        MANAGE_ACC,
        TECH_SUPPORT,
        FEEDBACK,
        INFORMATION
    }
    /* Contact Information */
    var fullname by mutableStateOf("")
    var phone by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var createdOn by mutableStateOf("")

    /* Billing Information */
    var streetAddress by mutableStateOf("")
    var apt by mutableStateOf("")
    var city by mutableStateOf("")
    var zipCode by mutableStateOf("")

    /* Card State */
    var isContactExpanded by  mutableStateOf(false)
    var isBillingExpanded by  mutableStateOf(false)
    var isManageAccExpanded by mutableStateOf(false)
    var isTechSupportExpanded by mutableStateOf(false)
    var isFeedbackExpanded by mutableStateOf(false)
    var isInformationExpanded by mutableStateOf(false)

    fun cardToggle(card: ProfileCard) {
        when(card) {
            CONTACT_INFO -> {
                isContactExpanded = isContactExpanded.not()
            }
            BILLING_INFO -> {
                isBillingExpanded = isBillingExpanded.not()
            }
            MANAGE_ACC -> {
                isManageAccExpanded = isManageAccExpanded.not()
            }
            TECH_SUPPORT -> {
                isTechSupportExpanded = isTechSupportExpanded.not()
            }
            FEEDBACK -> {
                isFeedbackExpanded = isFeedbackExpanded.not()
            }
            INFORMATION -> {
                isInformationExpanded = isInformationExpanded.not()
            }
        }
    }
    fun closeCards(vararg cards: ProfileCard){
        cards.forEach { card ->
            when(card){
                CONTACT_INFO -> {
                    isContactExpanded = false
                }
                BILLING_INFO -> {
                    isBillingExpanded = false
                }
                MANAGE_ACC -> {
                    isManageAccExpanded = false
                }
                TECH_SUPPORT -> {
                    isTechSupportExpanded = false
                }
                FEEDBACK -> {
                    isFeedbackExpanded = false
                }
                INFORMATION -> {
                    isInformationExpanded = false
                }
            }
        }
    }

}