package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumBillingAddress
import com.mwaibanda.momentum.domain.controller.BillingAddressController

class BillingAddressControllerImpl(driverFactory: DatabaseDriverFactory): BillingAddressController {
    private val database = Database(driverFactory)

    override fun addBillingAddress(
        streetAddress: String,
        apt: String?,
        city: String,
        zipCode: String,
        userId: String
    ) {
        database.insertBillingAddress(
            streetAddress = streetAddress,
            apt = apt,
            city = city,
            zipCode = zipCode,
            userId = userId
        )
    }

    override fun getBillingAddressByUserId(
        userId: String,
        onCompletion: (MomentumBillingAddress?) -> Unit
    ) {
        onCompletion(database.getBillingAddressByUserId(userId))
    }

    override fun updateBillingStreetByUserId(
        userId: String,
        streetAddress: String,
    ) {
        database.updateBillingStreetByUserId(
            userId = userId,
            streetAddress = streetAddress
        )
    }

    override fun updateBillingAptByUserId(userId: String, apt: String) {
        database.updateBillingAptByUserId(
            userId = userId,
            apt = apt
        )
    }

    override fun updateBillingCityByUserId(userId: String, city: String) {
        database.updateBillingCityByUserId(
            userId = userId,
            city = city
        )
    }

    override fun updateBillingZipCodeByUserId(
        userId: String,
        zipCode: String
    ) {
        database.updateBillingZipCodeByUserId(
            userId = userId,
            zipCode = zipCode
        )
    }

    override fun deleteBillingByUserId(userId: String) {
        database.deleteBillingByUserId(userId = userId)
    }
}