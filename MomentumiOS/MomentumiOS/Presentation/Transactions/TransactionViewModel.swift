//
//  TransactionViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/18/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import MomentumSDK

class TransactionViewModel: ObservableObject {
    @Inject private var controller: TransactionController
    @Published var transactions = [MomentumTransaction]()
    
  
    func addTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Bool
    ) {
        controller.addTransaction(
            description: description,
            date: date,
            amount: amount,
            isSeen: isSeen
        )
    }
    
    func getAllTransactions() {
        controller.getAllTransactions { transactions in
            self.transactions = transactions
        }
    }
    
    func deleteTransactionById(transactionId: Int) {
        controller.deleteTransactionById(transactionId: Int32(transactionId))
    }
    
    func deleteAllTransactions() {
        controller.deleteAllTransactions()
    }
    
    func getTransactionDate() -> String {
        let date = Date()
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MMMM d"
        return dateFormatter.string(from: date)
    }
}
