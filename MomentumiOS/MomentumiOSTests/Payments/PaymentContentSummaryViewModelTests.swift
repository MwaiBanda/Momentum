//
//  ContentSummaryTests.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/8/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

@testable import MomentumiOS
import XCTest

class PaymentContentSummaryViewModelTests: BaseXCTestCase {
    @Inject(named: TestConstants.contentViewModel)
    private var sut: PaymentSummaryContentViewModel
    
    override func setUpWithError() throws {
        try super.setUpWithError()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        _sut.release(named: TestConstants.contentViewModel)
    }
    
    func testSelectedLabels() throws {
        sut.processToggle(isActive: true, type: .offering)
        sut.processToggle(isActive: true, type: .missions)
        XCTAssertTrue(sut.selectedLabels.count == 2)
        XCTAssertEqual(sut.selectedLabels[0], .offering)
        sut.processToggle(isActive: false, type: .offering)
        XCTAssertEqual(sut.selectedLabels[0], .missions)
    }
    
    func testSetInitialTotalSetToFirstToggle() throws {
        sut.totalAmount = "44"
        sut.processToggle(isActive: true, type: .missions)
        XCTAssertEqual("44", sut.missionsAmount)
        sut.processToggle(isActive: false, type: .missions)
        XCTAssertEqual("0", sut.missionsAmount)

    }
    
    func testSubtractingTotalBetweenTwoToggles() throws {
        sut.totalAmount = "10"
        sut.processToggle(isActive: true, type: .offering)
        XCTAssertEqual("10", sut.offeringAmount)
        sut.processToggle(isActive: true, type: .tithe)
        sut.titheAmount = "7"
        sut.processAmount(amount: sut.titheAmount, type: .tithe)
        XCTAssertEqual("3", sut.offeringAmount)
        let res = String((Int(sut.titheAmount) ?? 0) + (Int(sut.offeringAmount) ?? 0))
        XCTAssertEqual(res, sut.totalAmount)
    }
    
    func testAddingTotalBetweenTwoToggles() throws {
        sut.totalAmount = "10"
        sut.processToggle(isActive: true, type: .offering)
        XCTAssertEqual("10", sut.offeringAmount)
        sut.processToggle(isActive: true, type: .tithe)
        sut.titheAmount = "7"
        sut.processAmount(amount: sut.titheAmount, type: .tithe)
        XCTAssertEqual("3", sut.offeringAmount)
        sut.offeringAmount = "6"
        sut.processAmount(amount: sut.offeringAmount, type: .offering)
        XCTAssertEqual("4", sut.titheAmount)
        let res = String((Int(sut.titheAmount) ?? 0) + (Int(sut.offeringAmount) ?? 0))
        XCTAssertEqual(res, sut.totalAmount)
    }
    
    
}
