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
    @State private var showingAlert = false
    @State private var showOtherModal = false
    @State private var otherLabel = ""

    var body: some View {
        ZStack {
            VStack {
                if let paymentSheet = paymentViewModel.paymentSheet {
                    VStack {
                        Divider()
                        PaymentSummaryContentView(
                            otherLabel: $otherLabel,
                            isEditingToggle: $isEditingToggle,
                            offerViewModel: offerViewModel,
                            contentViewModel: contentViewModel
                        ).onReceive(contentViewModel.$otherIsSelected) { _ in
                            print("[Other]: Selected")
                            if contentViewModel.otherIsSelected {
                                showOtherModal.toggle()
                            }
                        }
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
                            if contentViewModel.selectedLabels.isEmpty {
                                Button {
                                    showingAlert.toggle()
                                } label: {
                                    Text("Confirm")
                                        .fontWeight(.heavy)
                                        .frame(width: screenBounds.width - 30, height: 55)
                                }.buttonStyle(FilledButtonStyle(isDisabled: $isEditingToggle))
                                
                            } else {
                                PaymentSheet.PaymentButton(
                                    paymentSheet: paymentSheet,
                                    onCompletion: { result in
                                        paymentViewModel.onPaymentCompletion(paymentResult: result)
                                        Log.d(tag: "PAYMENT DESCRIPTION", contentViewModel.getTransactionDescription(otherLabel: otherLabel))

                                    }
                                ) {
                                    Text("Confirm")
                                        .fontWeight(.heavy)
                                        .frame(width: screenBounds.width - 30, height: 55)
                                }
                                .buttonStyle(FilledButtonStyle(isDisabled: $isEditingToggle))
                                .disabled(isEditingToggle)
                            }
                        }
                        Divider()
                        
                    }
                    .navigationTitle(paymentViewModel.isNavTitleHidden ? "" : "Payment Summary")
                    .navigationBarTitleDisplayMode(.inline)
                    .navigationBarBackButtonHidden(false)
                } else {
                    LoadingView()
                        .navigationBarBackButtonHidden(true)
                    
                }
            }
            if showOtherModal {
                Color.black.opacity(0.5).ignoresSafeArea(.all)
                    .navigationBarBackButtonHidden(true)
                VStack {
                    Spacer()
                    VStack(alignment: .leading) {
                        Text("What Ministry Are You Giving To?")
                            .bold()
                        TextEditor(text: $otherLabel)
                            .frame(height: 100)
                            .overlay(
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(.gray, lineWidth: 1)
                            )
                        HStack {
                            Button {
                                showOtherModal.toggle()
                                contentViewModel.selectedLabels.removeAll(where: {
                                    $0 == ToggleLabel.other
                                })
                                contentViewModel.otherIsSelected = false
                                contentViewModel.otherAmount = "0"
                            } label: {
                                HStack {
                                    Spacer()
                                    Text("Cancel")
                                        .padding(.vertical, 8)
                                    Spacer()
                                }
                            }.buttonStyle(FilledButtonStyle())
                            Spacer()
                            Button {
                                showOtherModal.toggle()
                            } label: {
                                HStack {
                                    Spacer()
                                    Text("Save")
                                        .padding(.vertical, 8)
                                    Spacer()
                                }
                            }.buttonStyle(FilledButtonStyle())
                        }
                    }.padding(20).frame(width: screenBounds.width * 0.8).background(Color.white).clipShape(RoundedCorner(radius: 10))
                    Spacer()
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
                                description: contentViewModel.getTransactionDescription(otherLabel: otherLabel),
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
                                    description: contentViewModel.getTransactionDescription(otherLabel: otherLabel),
                                    date: transactionViewModel.getTransactionDate(),
                                    amount: Double(offerViewModel.number) ?? 0.00,
                                    isSeen: false
                                )
                            }
                        case .canceled:
                            Log.d(tag: "PAYMENT DESCRIPTION", contentViewModel.getTransactionDescription(otherLabel: otherLabel))
                            break
                        case .failed(let error):
                            Log.d(tag: "ERROR", error.localizedDescription)
                        }
                    }
                
            }
        }
        .alert(isPresented: $showingAlert, content: {
            Alert(
                title: Text("No Ministry Selected"),
                message: Text("Please select a ministry your giving to, in order to proceed with this payment")
            )
        })
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
