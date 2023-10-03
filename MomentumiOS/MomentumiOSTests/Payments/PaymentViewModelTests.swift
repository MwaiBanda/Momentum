//
//  PaymentsTest.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/1/22.
//  Copyright © 2022 Momentum. All rights reserved.
//
@testable import TinyDi
@testable import MomentumiOS
@testable import MomentumSDK
import XCTest

class PaymentViewModelTests: BaseXCTestCase {
    @Inject private var sut: PaymentViewModel
    
    override func setUpWithError() throws {
        try super.setUpWithError()
    }
    
    override func tearDownWithError() throws {
        try super.tearDownWithError()
        _sut.release()
    }
    
    func testCheckout() {
        sut.checkout(
            transaction: Payment(
                amount: 10, fullname: "Mwai Banda",
                email: "mwai.developer@gmail.com",
                phone: "2190000000"
            )
        )
        XCTAssertNotNil(sut.response)
        guard let response = sut.response else { return assertionFailure("Nil response")}
        XCTAssertEqual(response.customer, "Mwai Banda")
        XCTAssertEqual(response.ephemeralKey, "2190000000")
    }
}
