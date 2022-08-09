//
//  FakeTransactionController.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/8/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable import MomentumSDK

class FakeTransactionController: TransactionController {
    var transactions = [MomentumTransaction]()
    func addTransaction(description: String, date: String, amount: Double, isSeen: Bool) {
        transactions.append(MomentumTransaction(id: Int64(transactions.count), description: description, date: date, amount: amount, is_seen: isSeen))
    }
    
    func deleteAllTransactions() {
        transactions.removeAll()
    }
    
    func deleteTransactionById(transactionId: Int32) {
        let index = transactions.firstIndex(where: { $0.id == transactionId })!
        transactions.remove(at: index)
    }
    
    func getAllTransactions(onCompletion: @escaping ([MomentumTransaction]) -> Void) {
        onCompletion(transactions)
    }
    
}
