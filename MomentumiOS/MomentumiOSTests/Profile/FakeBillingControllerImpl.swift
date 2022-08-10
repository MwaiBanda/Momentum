//
//  FakeBillingController.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/9/22.
//  Copyright © 2022 Momentum. All rights reserved.
//


@testable import MomentumiOS
@testable import MomentumSDK


class FakeBillingController: BillingAddressController {
    var addresses = [MomentumBillingAddress]()
    func addBillingAddress(streetAddress: String, apt: String?, city: String, zipCode: String, userId: String) {
        addresses.append(
            MomentumBillingAddress(
                id: Int64(addresses.count + 1),
                street_address: streetAddress,
                apt: apt,
                city: city,
                zip_code: zipCode,
                user_id: userId
            )
        )
    }
    
    func deleteBillingByUserId(userId: String) {
        let index = addresses.firstIndex(where: { $0.user_id == userId })!
        addresses.remove(at: index)
    }
    
    func getBillingAddressByUserId(userId: String, onCompletion: @escaping (MomentumBillingAddress?) -> Void) {
        let index = addresses.firstIndex(where: { $0.user_id == userId })!
        onCompletion(addresses[index])
    }
    
    func updateBillingAptByUserId(userId: String, apt: String) {
        let index = addresses.firstIndex(where: { $0.user_id == userId })!
        let address = addresses.remove(at: index)
        addresses.insert(
            MomentumBillingAddress(
                id: address.id,
                street_address: address.street_address,
                apt: apt,
                city: address.city,
                zip_code: address.zip_code,
                user_id: address.user_id
            ), at: index
        )
    }
    
    func updateBillingCityByUserId(userId: String, city: String) {
        let index = addresses.firstIndex(where: { $0.user_id == userId })!
        let address = addresses.remove(at: index)
        addresses.insert(
            MomentumBillingAddress(
                id: address.id,
                street_address: address.street_address,
                apt: address.apt,
                city: city,
                zip_code: address.zip_code,
                user_id: address.user_id
            ), at: index
        )
    }
    
    
    func updateBillingStreetByUserId(userId: String, streetAddress: String) {
        let index = addresses.firstIndex(where: { $0.user_id == userId })!
        let address = addresses.remove(at: index)
        addresses.insert(
            MomentumBillingAddress(
                id: address.id,
                street_address: streetAddress,
                apt: address.apt,
                city: address.city,
                zip_code: address.zip_code,
                user_id: address.user_id
            ), at: index
        )
    }
    
    func updateBillingZipCodeByUserId(userId: String, zipCode: String) {
        let index = addresses.firstIndex(where: { $0.user_id == userId })!
        let address = addresses.remove(at: index)
        addresses.insert(
            MomentumBillingAddress(
                id: address.id,
                street_address: address.street_address,
                apt: address.apt,
                city: address.city,
                zip_code: zipCode,
                user_id: address.user_id
            ), at: index
        )
    }
}

