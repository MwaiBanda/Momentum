//
//  FakePaymentController.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/1/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable import MomentumSDK

class FakePaymentController: PaymentController {
    func checkout(request: PaymentRequest, onCompletion: @escaping (PaymentResponse) -> Void) {
        let response: PaymentResponse = .init(
            publishableKey: "100101-111",
            paymentIntent: "Intent",
            customer: "Mwai Banda",
            ephemeralKey: "0007722"
        )
        onCompletion(response)
    }
}
