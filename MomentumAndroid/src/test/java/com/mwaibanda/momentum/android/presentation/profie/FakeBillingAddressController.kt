package com.mwaibanda.momentum.android.presentation.profie

import com.mwaibanda.momentum.data.db.MomentumBillingAddress
import com.mwaibanda.momentum.domain.controller.BillingAddressController

class FakeBillingAddressController: BillingAddressController {
    override fun addBillingAddress(
        streetAddress: String,
        apt: String?,
        city: String,
        zipCode: String,
        userId: String
    ) {
        TODO("Not yet implemented")
    }

    override fun getBillingAddressByUserId(
        userId: String,
        onCompletion: (MomentumBillingAddress?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateBillingStreetByUserId(userId: String, streetAddress: String) {
        TODO("Not yet implemented")
    }

    override fun updateBillingAptByUserId(userId: String, apt: String) {
        TODO("Not yet implemented")
    }

    override fun updateBillingCityByUserId(userId: String, city: String) {
        TODO("Not yet implemented")
    }

    override fun updateBillingZipCodeByUserId(userId: String, zipCode: String) {
        TODO("Not yet implemented")
    }

    override fun deleteBillingByUserId(userId: String) {
        TODO("Not yet implemented")
    }
}