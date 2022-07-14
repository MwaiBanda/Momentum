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

final class PaymentViewModel : ObservableObject {
    private var paymentController: PaymentController
    @Published private(set) var paymentSheet: PaymentSheet?
    @Published private(set) var paymentResult: PaymentSheetResult?
    @Published var isNavTitleHidden = false

    init(paymentController: PaymentController) {
        self.paymentController = paymentController
    }
    
    func onPaymentCompletion(paymentResult: PaymentSheetResult) {
        self.paymentResult = paymentResult
        switch paymentResult {
        case .completed:
            Log.d(tag:"Pay/Complete", message: "Payment Success")
            isNavTitleHidden = true
        case .canceled:
            Log.d(tag:"Pay/Cancelled", message: "Payment Cancelled")
            isNavTitleHidden = false
        case .failed(let error):
            Log.d(tag:"Pay/Failed", message: error.localizedDescription)
            isNavTitleHidden = true
        }
    }
    
    func checkout(request: PaymentRequest){
        paymentController.checkout(request: request) { response in
            STPAPIClient.shared.publishableKey = response.publishableKey
            var configuration = PaymentSheet.Configuration()
            configuration.merchantDisplayName = MultiplatformConstants.shared.merchantName
            configuration.customer = .init(id: response.customer, ephemeralKeySecret: response.ephemeralKey)
            configuration.primaryButtonColor = UIColor.init(red: 229/255, green: 95/255, blue: 31/255, alpha: 1)
            configuration.style = .alwaysLight
            configuration.returnURL = "momentumchurch://stripe-redirect"

            self.paymentSheet = PaymentSheet(
                paymentIntentClientSecret: response.paymentIntent,
                configuration: configuration
            )
        }
    }
}
