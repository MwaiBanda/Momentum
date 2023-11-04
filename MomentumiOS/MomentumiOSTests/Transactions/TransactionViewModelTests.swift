//
//  TransactionViewModelTests.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/8/22.
//  Copyright © 2022 Momentum. All rights reserved.
//
@testable import TinyDi
@testable import MomentumiOS
import XCTest

class TransactionViewModelTests: BaseXCTestCase {
    @Inject private var sut: TransactionViewModel
    
    override func setUpWithError() throws {
        try super.setUpWithError()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        _sut.release()
    }

    func testAddTransaction() throws {
        sut.addTransaction(
            description: "New transaction",
            date: "Aug 8",
            amount: 25.99,
            isSeen: false
        )
        sut.getMomemtumTransactions(userId: "1001")
        XCTAssertEqual(sut.transactions[0].id, 1)
        XCTAssertEqual(sut.transactions[0].amount, 25.99)
        sut.addTransaction(
            description: "New transaction",
            date: "Aug 8",
            amount: 55.99,
            isSeen: false
        )
        sut.getMomemtumTransactions(userId: "1001")
        XCTAssertEqual(sut.transactions[1].id, 2)
        XCTAssertEqual(sut.transactions[1].amount, 55.99)
    }
    
    func testDeleteAllTransactions() throws {
        sut.addTransaction(
            description: "New transaction",
            date: "Aug 8",
            amount: 25.99,
            isSeen: false
        )
        sut.addTransaction(
            description: "New transaction",
            date: "Aug 8",
            amount: 55.99,
            isSeen: false
        )
        sut.getMomemtumTransactions(userId: "1001")
        XCTAssertEqual(sut.transactions.count, 2)
        sut.deleteAllTransactions()
        sut.getMomemtumTransactions(userId: "1001")
        XCTAssertTrue(sut.transactions.isEmpty)

    }
    
    func testDeleteTransactionById() throws {
        sut.addTransaction(
            description: "New transaction",
            date: "Aug 8",
            amount: 25.99,
            isSeen: false
        )
        sut.addTransaction(
            description: "New transaction",
            date: "Aug 8",
            amount: 55.99,
            isSeen: false
        )
        sut.getMomemtumTransactions(userId: "1001")
        XCTAssertEqual(sut.transactions.count, 2)
        XCTAssertEqual(sut.transactions[0].id, 1)
        XCTAssertEqual(sut.transactions[0].amount, 25.99)
        sut.deleteTransactionById(transactionId: 1)
        sut.getMomemtumTransactions(userId: "1001")
        XCTAssertEqual(sut.transactions[0].id, 2)
        XCTAssertEqual(sut.transactions[0].amount, 55.99)
    }
}
