//
//  PaymentViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import MomentumSDK
import Stripe
import UIKit

class PaymentViewModel : ObservableObject {
    private var paymentController: PaymentController
    @Published var paymentSheet: PaymentSheet?
    @Published var paymentResult: PaymentSheetResult?
     
    init(paymentController: PaymentController) {
        self.paymentController = paymentController
    }
    
    func onPaymentCompletion(paymentResult: PaymentSheetResult) {
        self.paymentResult = paymentResult
    }
    
    func checkout(request: PaymentRequest){
        paymentController.checkout(request: request) { response in
            STPAPIClient.shared.publishableKey = response.publishableKey
            var configuration = PaymentSheet.Configuration()
            configuration.merchantDisplayName = MultiplatformConstants.shared.merchantName
            configuration.customer = .init(id: response.customer, ephemeralKeySecret: response.ephemeralKey)
            configuration.primaryButtonColor = UIColor(hex: Constants.momentumOrange)  
            configuration.style = .alwaysLight
            configuration.returnURL = "momentumchurch://stripe-redirect"

            self.paymentSheet = PaymentSheet(
                paymentIntentClientSecret: response.paymentIntent,
                configuration: configuration
            )
        }
    }
}
