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
    func provideAuth() {
        Auth.auth()
    }
  
    
     func inject() {
        Resolver.register { resolver in
            provideAuth()
            
            @Providing
            var db = {
                DatabaseDriverFactory()
            }()
            
            @Providing
            var paymentController: PaymentController = {
                PaymentControllerImpl()
            }()
            
            @Providing
            var transactionController: TransactionController = { TransactionControllerImpl(driverFactory: resolver.resolve())
            }()
            
            @Providing
            var authController: AuthController = {
                AuthControllerImpl()
            }()
        }
    }
    static let sharedInstance = DiRegistry()
    private init() { }
}
