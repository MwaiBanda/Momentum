//
//  Registry.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/1/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable import MomentumiOS
@testable import MomentumSDK

extension DiRegistry {
    
    @Provides
    func providesPaymentViewModel(){
        PaymentViewModel()
    }
    
    @Provides
    func providesOfferViewModel() {
        OfferViewModel()
    }
    
    @Provides
    func providesTransactionViewModel() {
        TransactionViewModel()
    }
    
    func injectTestDependencies() {
        Resolver.register { resolver in
            @Binds
            var foo: FooProviding = {
                Foo()
            }()
            
            @Binds
            var paymentController: PaymentController = {
                FakePaymentController()
            }()
            
            @Binds
            var transactionController: TransactionController = {
                FakeTransactionController()
            }()
            
            @Binds(named: TestConstants.contentViewModel)
            var contentViewModel = {
                PaymentSummaryContentViewModel()
            }()
            
            providesPaymentViewModel()
            providesOfferViewModel()
            providesTransactionViewModel()
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


