//
//  FakePaymentController.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/8/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable import MomentumSDK

class FakePaymentControllerImpl: PaymentController {
    func checkout(request: PaymentRequest, onCompletion: @escaping (Result<PaymentResponse>) -> Void) {
        let response: PaymentResponse = .init(
                  publishableKey: "100101-111",
                  paymentIntent: "Intent",
                  customer: request.fullname,
                  ephemeralKey: request.phone
              )
         
        onCompletion(
            ResultSuccess(data: response)
        )
    }
}
