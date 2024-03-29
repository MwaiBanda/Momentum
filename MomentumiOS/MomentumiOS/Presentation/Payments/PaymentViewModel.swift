//
//  PaymentViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import Foundation
import MomentumSDK
import Stripe
import StripePaymentSheet
import UIKit
import TinyDi


final class PaymentViewModel : ObservableObject {
    @Inject private var paymentController: PaymentController
    @Published private(set) var paymentSheet: PaymentSheet?
    @Published private(set) var paymentResult: PaymentSheetResult?
    @Published var response: PaymentResponse?
    @Published var isNavTitleHidden = false

   
    
    func onPaymentCompletion(paymentResult: PaymentSheetResult) {
        self.paymentResult = paymentResult
        switch paymentResult {
        case .completed:
            Log.d(tag:"Pay/Complete", "Payment Success")
            isNavTitleHidden = true
            paymentSheet = nil
        case .canceled:
            Log.d(tag:"Pay/Cancelled", "Payment Cancelled")
            isNavTitleHidden = false
        case .failed(let error):
            Log.d(tag:"Pay/Failed", error.localizedDescription)
            isNavTitleHidden = true
        }
    }
    
    func checkout(transaction: Payment, onCompletion: @escaping () -> Void = {}){
        paymentController.checkout(request: transaction) { response in
            if let response = response.data {
                self.response = response
                onCompletion()
            } else if let error = response.message {
                Log.d(tag: "Pay/Failure", error)
            }
        }
    }
    
    
    
    func setUpPaymentSheet() {
        guard let response = response else { return }
        STPAPIClient.shared.publishableKey = response.publishableKey
        var configuration = PaymentSheet.Configuration()
        configuration.applePay = .init(merchantId: "merchant.mwaibanda.momentum", merchantCountryCode: "US")
        configuration.merchantDisplayName = MultiplatformConstants.shared.MERCHANT_NAME
        configuration.customer = .init(id: response.customer, ephemeralKeySecret: response.ephemeralKey)
        configuration.primaryButtonColor = UIColor.init(red: 229/255, green: 95/255, blue: 31/255, alpha: 1)
        configuration.style = .alwaysLight
        configuration.returnURL = "momentum-church://stripe-redirect"

        self.paymentSheet = PaymentSheet(
            paymentIntentClientSecret: response.paymentIntent,
            configuration: configuration
        )
    }
}
