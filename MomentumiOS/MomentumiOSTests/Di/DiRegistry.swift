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
    func providePaymentViewModel(){
        PaymentViewModel()
    }
    
    func injectTestDependencies() {
        Resolver.register { resolver in
            
            @Binds
            var paymentController: PaymentController = {
                FakePaymentController()
            }()
            
            
            @Binds
            var foo: FooProviding = {
                Foo()
            }()
            
            providePaymentViewModel()
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


