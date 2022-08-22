package com.mwaibanda.momentum.android.presentation.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.*
import com.mwaibanda.momentum.domain.controller.BillingAddressController
import com.mwaibanda.momentum.domain.controller.LocalDefaultsController
import com.mwaibanda.momentum.domain.controller.UserController
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.utils.MultiplatformConstants

class ProfileViewModel(
    private val userController: UserController,
    private val billingAddressController: BillingAddressController,
    private val localDefaultsController: LocalDefaultsController
) : ViewModel() {
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
    var isContactExpanded by mutableStateOf(false)
    var isBillingExpanded by mutableStateOf(false)
    var isManageAccExpanded by mutableStateOf(false)
    var isTechSupportExpanded by mutableStateOf(false)
    var isFeedbackExpanded by mutableStateOf(false)
    var isInformationExpanded by mutableStateOf(false)

    fun cardToggle(card: ProfileCard) {
        when (card) {
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

    fun closeCards(vararg cards: ProfileCard) {
        cards.forEach { card ->
            when (card) {
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

    fun addUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String
    ) {
        userController.addMomentumUser(
            fullname = fullname,
            phone = phone,
            password = password,
            email = email,
            createdOn = createdOn,
            userId = userId
        ) {
            userController.postUser(
                user = User(
                    fullname = fullname,
                    email = email,
                    phone = phone,
                    userId = userId,
                    createdOn = createdOn
                )
            )
            billingAddressController.addBillingAddress(
                streetAddress = "",
                apt = "",
                city = "",
                zipCode = "",
                userId = userId
            )
        }
    }

    fun getContactInformation(userId: String, onCompletion: () -> Unit = {}) {
        userController.getMomentumUserById(userId = userId) { user ->
            if (user != null) {
                fullname = user.fullname
                phone = user.phone
                email = user.email
                password = user.password
                createdOn = user.created_on
                onCompletion()
            } else {
                localDefaultsController.getString(key = MultiplatformConstants.PASSWORD) { password ->
                    this.password = password
                    userController.getUser(userId) { user ->
                        fullname = user.fullname
                        phone = user.phone
                        email = user.email
                        createdOn = user.createdOn
                        addUser(
                            fullname = fullname,
                            phone = phone,
                            password = password,
                            email = email,
                            createdOn = createdOn,
                            userId = userId
                        )
                        onCompletion()
                    }
                }
            }
        }
    }

    fun getBillingInformation(userId: String) {
        billingAddressController.getBillingAddressByUserId(userId = userId) { address ->
            if (address != null) {
                streetAddress = address.street_address
                apt = address.apt ?: ""
                city = address.city
                zipCode = address.zip_code
            }
        }
    }

    fun updateFullname(userId: String) {
        userController.updateMomentumUserFullnameByUserId(
            userId = userId,
            fullname = fullname
        ) {
            userController.updateUserFullname(userID = userId, fullname = fullname)
        }
    }

    fun updatePhone(userId: String) {
        userController.updateMomentumUserPhoneByUserId(
            userId = userId,
            phone = phone
        ) {
            userController.updatePhoneByUserId(userId = userId, phone = phone)
        }
    }

    fun updateEmail(userId: String) {
        userController.updateMomentumUserEmailByUserId(
            userId = userId,
            email = email
        ) {
            userController.updateUserEmail(userID = userId, email = email)
        }
    }

    fun updatePassword(userId: String) {
        userController.updateMomentumUserPasswordUserId(userId = userId, password = password) {
            Log.d("Profile/Password", "Password Updated")
        }
    }

    fun updateStreetAddress(userId: String) {
        billingAddressController.updateBillingStreetByUserId(
            userId = userId,
            streetAddress = streetAddress
        )
    }

    fun updateApt(userId: String) {
        billingAddressController.updateBillingAptByUserId(userId = userId, apt = apt)
    }

    fun updateCity(userId: String) {
        billingAddressController.updateBillingCityByUserId(userId = userId, city = city)
    }

    fun updateZipCode(userId: String) {
        billingAddressController.updateBillingZipCodeByUserId(userId = userId, zipCode = zipCode)
    }

    fun deleteUser(userId: String, onCompletion: () -> Unit) {
        userController.deleteMomentumUserByUserId(userId = userId) {
            userController.deleteUser(userID = userId) {
                onCompletion()
            }
        }
    }

    fun deleteBillingAddress(userId: String) {
        billingAddressController.deleteBillingByUserId(userId = userId)
    }
}