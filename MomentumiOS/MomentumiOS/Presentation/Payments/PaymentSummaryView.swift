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
import StripePayments
import StripePaymentSheet

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
                    .navigationBarTitleDisplayMode(.large)
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
                            let transaction = Transaction(
                                fullname: profileViewModel.fullname,
                                email: profileViewModel.email,
                                phone: profileViewModel.phone,
                                description: contentViewModel.getTransactionDescription(),
                                amount: Int32(Double(offerViewModel.number) ?? 0.00),
                                date: transactionViewModel.getTransactionDate(),
                                userId: session.currentUser?.id ?? ""
                            )
                            transactionViewModel.postTransactionInfo(transaction: transaction) {
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
                    transaction: Transaction(
                        fullname: profileViewModel.fullname,
                        email: profileViewModel.email,
                        phone: profileViewModel.phone,
                        description: "",
                        amount: Int32((Double(offerViewModel.number) ?? 0.00) * 100),
                        date: transactionViewModel.getTransactionDate(),
                        userId: session.currentUser?.id ?? ""
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
