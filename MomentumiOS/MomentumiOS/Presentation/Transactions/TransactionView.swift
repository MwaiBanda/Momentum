//
//  TransactionView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/18/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK

struct TransactionView: View {
    @EnvironmentObject var session: Session
    @StateObject private var transactionViewModel = TransactionViewModel()
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
            if transactionViewModel.transactions.isEmpty {
                Spacer()
                Text("No Transactions")
            }
            Spacer()
        }
        .onAppear {
            if !(session.currentUser?.isGuest ?? true) {
                transactionViewModel.getMomemtumTransactions(userId: session.currentUser?.id ?? "")
            }
        }
    }
}

struct TransactionView_Previews: PreviewProvider {
    static var previews: some View {
        TransactionView()
    }
}



