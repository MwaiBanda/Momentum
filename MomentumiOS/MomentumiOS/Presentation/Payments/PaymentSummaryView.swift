//
//  PaymentSummaryView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MomentumSDK
import Stripe

struct PaymentSummaryView: View {
    @ObservedObject var offerViewModel: OfferViewModel
    @StateObject private var paymentViewModel = PaymentViewModel(paymentController: PaymentControllerImpl())
    var body: some View {
        ZStack {
            VStack {
                if let paymentSheet = paymentViewModel.paymentSheet {
                    VStack {
                      
                        Divider()
                    Spacer()
                    PaymentSheet.PaymentButton(
                        paymentSheet: paymentSheet,
                        onCompletion: paymentViewModel.onPaymentCompletion
                    ) {
                        Text("Confirm")
                            .fontWeight(.heavy)
                            .frame(width: screenBounds.width - 30, height: 55)
                    }.buttonStyle(FilledButtonStyle())
                        Divider()

                    }
                    .navigationBarBackButtonHidden(false)
                    .navigationTitle("Payment Summary")
                } else {
                    VStack {
                        Spacer()
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: Color(hex: Constants.momentumOrange)))

                        Spacer()

                    }.navigationBarBackButtonHidden(true)

                }
            }.onAppear {
                paymentViewModel.checkout(request: PaymentRequest(amount: Int32((Double(offerViewModel.number) ?? 0.00) * 100)))
            }
            if let paymentResult = paymentViewModel.paymentResult {
                PaymentResultView(result: paymentResult)
            }
        }
    }
}

struct PaymentSummaryView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentSummaryView(offerViewModel: OfferViewModel())
    }
}
