//
//  PaymentSummaryView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
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
                        PaymentSummaryContentView(offerViewModel: offerViewModel)
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
                    .navigationTitle(paymentViewModel.isNavTitleHidden ? "" : "Payment Summary")
                    .navigationBarBackButtonHidden(false)
                    
                } else {
                    LoadingView()
                        .navigationBarBackButtonHidden(true)
                    
                }
            }.onAppear {
                paymentViewModel.checkout(request: PaymentRequest(amount: Int32((Double(offerViewModel.number) ?? 0.00) * 100)))
            }
            if let paymentResult = paymentViewModel.paymentResult {
                
                PaymentResultView(result: paymentResult)
                    .navigationBarHidden(true)
                
                
            }
        }
    }
   
}

struct PaymentSummaryView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentSummaryView(offerViewModel: OfferViewModel())
    }
}
