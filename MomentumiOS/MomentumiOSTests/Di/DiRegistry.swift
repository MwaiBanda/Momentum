//
//  Registry.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/1/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable import MomentumiOS
@testable import MomentumSDK
@testable import TinyDi

extension DependencyRegistry {
    
    @Singleton
    func providesPaymentViewModel(){
        PaymentViewModel()
    }
    
    @Singleton
    func providesOfferViewModel() {
        OfferViewModel()
    }
    
    @Singleton
    func providesTransactionViewModel() {
        TransactionViewModel()
    }
    
    @Singleton
    func providesProfileViewModel() {
        ProfileViewModel()
    }
    
    @Singleton
    func providesSession() {
        Session()
    }
    
    func injectTestDependencies() {
        TDi.inject { resolver in
            @Binds
            var foo: FooProviding = {
                Foo()
            }()
            
            @Binds
            var paymentController: PaymentController = {
                FakePaymentControllerImpl()
            }()
            
            @Binds
            var transactionController: TransactionController = {
                FakeTransactionControllerImpl()
            }()
            
            @Binds
            var userController: UserController = {
                FakeUserControllerImpl()
            }()
            
            @Binds
            var authController: AuthController = {
                FakeAuthControllerImpl()
            }()
            
            @Binds
            var billingController: BillingAddressController = {
                FakeBillingController()
            }()
            
            @Binds(named: TestConstants.contentViewModel)
            var contentViewModel = {
                PaymentSummaryContentViewModel()
            }()
            
            providesPaymentViewModel()
            providesOfferViewModel()
            providesTransactionViewModel()
            providesProfileViewModel()
            providesSession()
        }
    }
}

protocol FooProviding {
    func bar() -> String
}


class Foo: FooProviding {
    func bar() -> String {
        return "Bar"
    }

}


