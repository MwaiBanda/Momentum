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
    @EnvironmentObject var session: Session
    @ObservedObject var offerViewModel: OfferViewModel
    @StateObject private var paymentViewModel = PaymentViewModel()
    @StateObject private var contentViewModel = PaymentSummaryContentViewModel()
    @StateObject private var transactionViewModel = TransactionViewModel()
    @StateObject private var profileViewModel = ProfileViewModel()
    
    var body: some View {
        ZStack {
            VStack {
                if let paymentSheet = paymentViewModel.paymentSheet {
                    VStack {
                        Divider()
                        PaymentSummaryContentView(
                            offerViewModel: offerViewModel,
                            contentViewModel: contentViewModel
                        )
                        Spacer()
                        PaymentSheet.PaymentButton(
                            paymentSheet: paymentSheet,
                            onCompletion: paymentViewModel.onPaymentCompletion
                        ) {
                            Text("Confirm")
                                .fontWeight(.heavy)
                                .frame(width: screenBounds.width - 30, height: 55)
                        }
                        .buttonStyle(FilledButtonStyle())
                        Divider()
                        
                    }
                    .navigationTitle(paymentViewModel.isNavTitleHidden ? "" : "Payment Summary")
                    .navigationBarBackButtonHidden(false)
                } else {
                    LoadingView()
                        .navigationBarBackButtonHidden(true)
                    
                }
            }
            if let paymentResult = paymentViewModel.paymentResult {
                
                PaymentResultView(result: paymentResult)
                    .navigationBarHidden(true)
                    .onAppear {
                        switch paymentResult {
                        case .completed:
                            let request = PaymentRequest(
                                fullname: profileViewModel.fullname,
                                email: profileViewModel.email,
                                phone: profileViewModel.phone,
                                description: contentViewModel.getTransactionDescription()
                                    .replacingOccurrences(of: ",", with: "<br>"),
                                amount: Int32(Double(offerViewModel.number) ?? 0.00)
                            )
                            transactionViewModel.postTransactionInfo(request: request) {
                                transactionViewModel.addTransaction(
                                    description: contentViewModel.getTransactionDescription(),
                                    date: transactionViewModel.getTransactionDate(),
                                    amount: Double(offerViewModel.number) ?? 0.00,
                                    isSeen: false
                                )
                            }
                        case .canceled:
                            break
                        case .failed(let error):
                            Log.d(tag: "ERROR", message: error.localizedDescription)
                        }
                    }
                
            }
        }
        .onAppear {
            profileViewModel.getContactInformation(userId: session.currentUser?.id ?? "") {
                paymentViewModel.checkout(
                    request: PaymentRequest(
                        fullname: profileViewModel.fullname,
                        email: profileViewModel.email,
                        phone: profileViewModel.phone,
                        description: "",
                        amount: Int32((Double(offerViewModel.number) ?? 0.00) * 100)
                    )
                ) {
                    paymentViewModel.setUpPaymentSheet()
                }
            }
        }
    }
}

struct PaymentSummaryView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentSummaryView(offerViewModel: OfferViewModel())
    }
}
