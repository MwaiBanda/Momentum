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

final class DiRegistry {
    
    @Provides
    private func providesAuth() {
        Auth.auth()
    }
    
    @Provides
    private func providesDBFactory() {
        DatabaseDriverFactory()
    }
    
  
    func inject() {
        Resolver.register { resolver in
            providesAuth()
            providesDBFactory()
            
            @Binds
            var paymentController: PaymentController = {
                PaymentControllerImpl()
            }()
            
            @Binds
            var transactionController: TransactionController = {
                TransactionControllerImpl(
                    driverFactory: resolver.resolve()
                )
            }()
            
            @Binds
            var userController: UserController = {
                UserControllerImpl(
                    driverFactory: resolver.resolve()
                )
            }()
            
            @Binds
            var billingAddressController: BillingAddressController = {
                BillingAddressControllerImpl(
                    driverFactory: resolver.resolve()
                )
            }()
            
            @Binds
            var authController: AuthController = {
                AuthControllerImpl()
            }()
        }
    }
    static let shared = DiRegistry()
    private init() { }
}
