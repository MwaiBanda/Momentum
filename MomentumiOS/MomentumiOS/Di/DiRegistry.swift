//
//  DiRegistry.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/21/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import FirebaseAuth
import MomentumSDK

struct DiRegistry {
    static func inject() {
        Resolver.inject(Auth.auth())
        Resolver.inject(PaymentControllerImpl() as PaymentController)
        Resolver.inject(TransactionControllerImpl(driverFactory: DatabaseDriverFactory()) as TransactionController)
    }
    private init() { }
}
