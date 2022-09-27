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
import AVFoundation

final class DiRegistry {
    
    @Singleton
    private func providesAuth() {
        Auth.auth()
    }
    
    @Singleton
    private func providesDBFactory() {
        DatabaseDriverFactory()
    }
    
    @Singleton
    private func providesPlayer() {
        AVPlayer()
    }
  
    func inject() {
        Resolver.register { resolver in
            providesAuth()
            providesDBFactory()
            providesPlayer()
            
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
            var localDefaultsController: LocalDefaultsController = {
                LocalDefaultsControllerImpl()
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
    func clear(onCompletion: @escaping () -> Void) {
        Resolver.clear(onCompletion: onCompletion)
    }
    static let shared = DiRegistry()
    private init() { }
}
