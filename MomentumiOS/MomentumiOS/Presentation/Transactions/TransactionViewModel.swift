//
//  TransactionViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/18/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import Foundation
import MomentumSDK
import TinyDi

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
    
    private func addTransactions(transactions: [MomentumTransaction]) {
        controller.addTransactions(transactions: transactions)
    }
    
     func getTransactions(userId: String) {
//         controller.deleteAllTransactions()
        controller.getTransactions(userId: userId) { [self] res in
            if let res = res.data {
                transactions = (res as! [Transaction]).map({ $0.toMomentumTransaction() })
                if !transactions.isEmpty {
                    addTransactions(transactions: transactions)
                }
            }
        }
    }
    
     
    func postTransactionInfo(transaction: Transaction, onCompletion: @escaping () -> Void = {}) {
        controller.postTransactionInfo(transaction: transaction) { response in
            if let response = response.data {
                if response == 200 {
                    Log.d(tag: "Pay/Post/Status", message: response)
                    onCompletion()
                }
            } else if let error = response.message {
                Log.d(tag: "Pay/Failure", message: error)
            }
        }
    }
    func getMomemtumTransactions(userId: String) {
        controller.getMomentumTransactions { [unowned self] transactions in
            self.transactions = transactions
            if transactions.isEmpty {
                getTransactions(userId: userId)
            }
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
