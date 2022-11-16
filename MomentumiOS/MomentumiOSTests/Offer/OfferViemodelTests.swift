//
//  OfferViemodelTests.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/8/22.
//  Copyright © 2022 Momentum. All rights reserved.
//
@testable import TinyDi
@testable import MomentumiOS
import XCTest

class OfferViemodelTests: BaseXCTestCase {
    @Inject private var sut: OfferViewModel
    
    override func setUpWithError() throws {
        try super.setUpWithError()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        _sut.release()
    }

    func testNumber() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "0")
        sut.processInput(button: "0")
        sut.processInput(button: "0")
        XCTAssertEqual("3000", sut.number)
    }
    func testDisplayNumber() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "0")
        sut.processInput(button: "0")
        sut.processInput(button: "0")
        XCTAssertEqual("$3,000", sut.displayNumber)
    }
    func testDecimalNumber() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "0")
        sut.processInput(button: ".")
        XCTAssertEqual("30.00", sut.number)
        sut.processInput(button: "9")
        sut.processInput(button: "9")
        XCTAssertEqual("30.99", sut.number)
    }
    
    func testDecimalDisplayNumber() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "0")
        sut.processInput(button: ".")
        XCTAssertEqual("$30.00", sut.displayNumber)
        sut.processInput(button: "9")
        sut.processInput(button: "9")
        XCTAssertEqual("$30.99", sut.displayNumber)
    }
   
    func testDecimalNumberDecimalBackspace() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "0")
        sut.processInput(button: ".")
        XCTAssertEqual("30.00", sut.number)
        sut.processInput(button: "9")
        sut.processInput(button: "9")
        XCTAssertEqual("30.99", sut.number)
        sut.processInput(button: "<")
        sut.processInput(button: "<")
        XCTAssertEqual("30.00", sut.number)
        sut.processInput(button: "<")
        XCTAssertEqual("30", sut.number)
    }
    
    func testDecimalDisplayNumberDecimalBackspace() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "0")
        sut.processInput(button: ".")
        XCTAssertEqual("$30.00", sut.displayNumber)
        sut.processInput(button: "9")
        sut.processInput(button: "9")
        XCTAssertEqual("$30.99", sut.displayNumber)
        sut.processInput(button: "<")
        sut.processInput(button: "<")
        XCTAssertEqual("$30.00", sut.displayNumber)
        sut.processInput(button: "<")
        XCTAssertEqual("$30", sut.displayNumber)
    }
    
    func testDecimalNumberBackspace() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "4")
        sut.processInput(button: "5")
        XCTAssertEqual("345", sut.number)
        sut.processInput(button: "<")
        XCTAssertEqual("34", sut.number)
        sut.processInput(button: "<")
        XCTAssertEqual("3", sut.number)
        sut.processInput(button: "<")
        XCTAssertEqual("0", sut.number)

    }
    func testDecimalDisplayNumberBackspace() throws {
        sut.processInput(button: "3")
        sut.processInput(button: "4")
        sut.processInput(button: "5")
        XCTAssertEqual("$345", sut.displayNumber)
        sut.processInput(button: "<")
        XCTAssertEqual("$34", sut.displayNumber)
        sut.processInput(button: "<")
        XCTAssertEqual("$3", sut.displayNumber)
        sut.processInput(button: "<")
        XCTAssertEqual("$0", sut.displayNumber)
    }
}
