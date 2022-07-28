package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.MomentumBillingAddress

interface BillingAddressController {
    fun addBillingAddress(
        streetAddress: String,
        apt: String?,
        city: String,
        zipCode: String,
        userId: String,
    )

    fun getBillingAddressByUserId(userId: String, onCompletion: (MomentumBillingAddress?) -> Unit)

    fun updateBillingStreetByUserId(
        userId: String,
        streetAddress: String,
    )

    fun updateBillingAptByUserId(
        userId: String,
        apt: String,
    )

    fun updateBillingCityByUserId(
        userId: String,
        city: String,
    )

    fun updateBillingZipCodeByUserId(
        userId: String,
        zipCode: String,
    )

    fun deleteBillingByUserId(userId: String)
}