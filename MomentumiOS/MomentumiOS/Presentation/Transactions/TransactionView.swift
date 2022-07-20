//
//  TransactionView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/18/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import MomentumSDK

struct TransactionView: View {
    @StateObject private var transactionViewModel = TransactionViewModel(controller: TransactionControllerImpl(driverFactory: DatabaseDriverFactory()))
    @Environment(\.presentationMode) private var presentationMode
    var body: some View {
        VStack {
            HStack {
            Text("Transactions")
                    .font(.title3)
                    .fontWeight(.heavy)
                Spacer()
                Button { presentationMode.wrappedValue.dismiss() } label: {
                    Image(systemName: "xmark.circle.fill")
                        .imageScale(.large)
                        .foregroundColor(.gray)
                }
                
            }.padding()
            Divider()
            ForEach(transactionViewModel.transactions, id: \.id) { transaction in
                TransactionLabel(
                    description: transaction.description_,
                    date: transaction.date,
                    amount: transaction.amount
                )
                Divider()
            }
            Spacer()
        }
        .onAppear {
            transactionViewModel.getAllTransactions()
//            transactionViewModel.addTransaction(description: "$10: Offering, $20: Tithe", date: "July 19", amount: 30.99, isSeen: false)
        }
    }
}

struct TransactionView_Previews: PreviewProvider {
    static var previews: some View {
        TransactionView()
    }
}



