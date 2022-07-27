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
    func providesAuth() {
        Auth.auth()
    }
    
    @Provides
    func providesDBFactory() {
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
            var authController: AuthController = {
                AuthControllerImpl()
            }()
        }
    }
    static let sharedInstance = DiRegistry()
    private init() { }
}
