package com.mwaibanda.momentum.android.presentation.profile

import com.mwaibanda.momentum.data.db.MomentumBillingAddress
import com.mwaibanda.momentum.domain.controller.BillingAddressController

class FakeBillingAddressController: BillingAddressController {
    private val addresses = mutableListOf<MomentumBillingAddress>()
    override fun addBillingAddress(
        streetAddress: String,
        apt: String?,
        city: String,
        zipCode: String,
        userId: String
    ) {
        addresses.add(
            MomentumBillingAddress(
                id = (addresses.count() + 1).toLong(),
                street_address = streetAddress,
                apt = apt,
                city = city,
                zip_code = zipCode,
                user_id = userId
            )
        )
    }

    override fun getBillingAddressByUserId(
        userId: String,
        onCompletion: (MomentumBillingAddress?) -> Unit
    ) {
        val index = addresses.indexOfFirst { it.user_id == userId }
        onCompletion(addresses[index])
    }

    override fun updateBillingStreetByUserId(userId: String, streetAddress: String) {
        val index = addresses.indexOfFirst { it.user_id == userId }
        val address = addresses[index]
        addresses.set(
            index = index,
            MomentumBillingAddress(
                id = address.id,
                street_address = streetAddress,
                apt = address.apt,
                city = address.city,
                zip_code = address.zip_code,
                user_id = address.user_id
            )
        )
    }

    override fun updateBillingAptByUserId(userId: String, apt: String) {
        val index = addresses.indexOfFirst { it.user_id == userId }
        val address = addresses[index]
        addresses.set(
            index = index,
            MomentumBillingAddress(
                id = address.id,
                street_address = address.street_address,
                apt = apt,
                city = address.city,
                zip_code = address.zip_code,
                user_id = address.user_id
            )
        )
    }

    override fun updateBillingCityByUserId(userId: String, city: String) {
        val index = addresses.indexOfFirst { it.user_id == userId }
        val address = addresses[index]
        addresses.add(
            index = index,
            MomentumBillingAddress(
                id = address.id,
                street_address = address.street_address,
                apt = address.apt,
                city = city,
                zip_code = address.zip_code,
                user_id = address.user_id
            )
        )
    }

    override fun updateBillingZipCodeByUserId(userId: String, zipCode: String) {
        val index = addresses.indexOfFirst { it.user_id == userId }
        val address = addresses[index]
        addresses.set(
            index = index,
            MomentumBillingAddress(
                id = address.id,
                street_address = address.street_address,
                apt = address.apt,
                city = address.city,
                zip_code = zipCode,
                user_id = address.user_id
            )
        )
    }

    override fun deleteBillingByUserId(userId: String) {
        val index = addresses.indexOfFirst { it.user_id == userId }
        addresses.removeAt(index)
    }
}