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


struct TransactionLabel: View{
    var description: String
    var date: String
    var amount: Double

    var body: some View {
        HStack {
            Image("momentum")
                .resizable()
                .aspectRatio( contentMode: .fit)
                .frame(width: 45, height: 45)
            HStack {
                VStack(alignment: .leading) {
                    Text("Momentum")
                        .font(.headline)
                        .fontWeight(.heavy)
                    Text("Paid \(description)")
                        .font(.subheadline)
                        .foregroundColor(Color(.systemGray2))

                }
                Spacer()
                VStack(alignment: .trailing) {
                    Text("$\(amount, specifier: "%.2f")")
                        .font(.headline)
                        .fontWeight(.heavy)
                    Text(date)
                        .font(.subheadline)
                        .foregroundColor(Color(.systemGray2))
                }
            }
        }
        .padding()
    }
}
