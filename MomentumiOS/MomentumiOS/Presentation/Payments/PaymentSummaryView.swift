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
    @State private var isEditingToggle = false
    var body: some View {
        ZStack {
            VStack {
                if let paymentSheet = paymentViewModel.paymentSheet {
                    VStack {
                        Divider()
                        PaymentSummaryContentView(
                            isEditingToggle: $isEditingToggle,
                            offerViewModel: offerViewModel,
                            contentViewModel: contentViewModel
                        )
                        Spacer()
                        if isEditingToggle {
                            Divider()
                            HStack {
                                Spacer()
                                Button {
                                    let keyWindow = UIApplication.shared.connectedScenes
                                        .filter({$0.activationState == .foregroundActive})
                                        .map({$0 as? UIWindowScene})
                                        .compactMap({$0})
                                        .first?.windows
                                        .filter({$0.isKeyWindow}).first
                                    keyWindow?.endEditing(true)
                                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.15, execute: {
                                        isEditingToggle = false
                                    })
                                } label: {
                                    Text("Done")
                                        .fontWeight(.bold)
                                }
                                .padding([.top, .bottom, .trailing], 5)
                                .padding(.trailing, 10)
                            }
                        } else {
                            PaymentSheet.PaymentButton(
                                paymentSheet: paymentSheet,
                                onCompletion: paymentViewModel.onPaymentCompletion
                            ) {
                                Text("Confirm")
                                    .fontWeight(.heavy)
                                    .frame(width: screenBounds.width - 30, height: 55)
                            }
                            .buttonStyle(FilledButtonStyle(isDisabled: $isEditingToggle))
                            .disabled(isEditingToggle)
                        }
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
                                id: "",
                                description: contentViewModel.getTransactionDescription(),
                                amount: Int32(Double(offerViewModel.number) ?? 0.00),
                                date: transactionViewModel.getTransactionDate(),
                                createdOn: "",
                                user: User(
                                    fullname: profileViewModel.fullname,
                                    email: profileViewModel.email,
                                    phone: profileViewModel.phone,
                                    userId: session.currentUser?.id ?? "",
                                    createdOn: ""
                                )
                              
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
                    transaction: Payment(
                        amount: Int32((Double(offerViewModel.number) ?? 0.00) * 100),
                        fullname: profileViewModel.fullname,
                        email: profileViewModel.email,
                        phone: profileViewModel.phone
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
