//
//  ControllerModule.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/14/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import Foundation
import TinyDi
import MomentumSDK

@TinyModule
func controllerModule(resolver: TinyDi) {
    Module(
        Single<PaymentController>(PaymentControllerImpl()),
        Single<TransactionController>(TransactionControllerImpl(driverFactory: resolver.resolve())),
        Single<LocalDefaultsController>(LocalDefaultsControllerImpl()),
        Single<UserController>(UserControllerImpl(driverFactory: resolver.resolve())),
        Single<BillingAddressController>(BillingAddressControllerImpl(driverFactory: resolver.resolve())),
        Single<AuthController>(AuthControllerImpl(controller: resolver.resolve())),
        Single<SermonController>(SermonControllerImpl(driverFactory: resolver.resolve())),
        Single<MealController>(MealControllerImpl()),
        Single<MessageController>(MessageControllerImpl())
    )
}
