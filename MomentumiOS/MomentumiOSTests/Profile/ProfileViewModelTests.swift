//
//  ProfileViewModelTests.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/9/22.
//  Copyright © 2022 Momentum. All rights reserved.
//
@testable import TinyDi
@testable import MomentumiOS
import XCTest

class ProfileViewModelTests: BaseXCTestCase {
    @Inject private var sut: ProfileViewModel
    
    override func setUpWithError() throws {
        try super.setUpWithError()
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        _sut.release()
    }
    
    func testCardToggle() {
        XCTAssertFalse(sut.isBillingInfoExpanded)
        sut.cardToggle(card: .billingInfo)
        XCTAssertTrue(sut.isBillingInfoExpanded)
        sut.cardToggle(card: .billingInfo)
        XCTAssertFalse(sut.isBillingInfoExpanded)
    }
    
    func testCloseCards() throws {
        XCTAssertFalse(sut.isBillingInfoExpanded)
        XCTAssertFalse(sut.isContactInfoExpanded)
        XCTAssertFalse(sut.isFeedbackExpanded)
        sut.cardToggle(card: .billingInfo)
        sut.cardToggle(card: .contactInfo)
        sut.cardToggle(card: .feedback)
        XCTAssertTrue(sut.isBillingInfoExpanded)
        XCTAssertTrue(sut.isContactInfoExpanded)
        XCTAssertTrue(sut.isFeedbackExpanded)
        sut.closeCards(cards: .billingInfo, .contactInfo, .feedback)
        XCTAssertFalse(sut.isBillingInfoExpanded)
        XCTAssertFalse(sut.isContactInfoExpanded)
        XCTAssertFalse(sut.isFeedbackExpanded)
    }

    func testAddUser() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.getContactInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.fullname, "Mwai Banda")
    }
    
    func testFullnameUpdate() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.fullname = "Mwai"
        sut.updateFullname(userId: "M1001-B00")
        sut.fullname = "Mwai Banda"
        sut.getContactInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.fullname, "Mwai")
    }
    
    func testPhoneUpdate() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.phone = "2190001001"
        sut.updatePhone(userId: "M1001-B00")
        sut.phone = "2190000000"
        sut.getContactInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.phone, "2190001001")
    }
    
    func testPasswordUpdate() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.password = "******19"
        sut.updatePassword(userId: "M1001-B00")
        sut.password = "*******"
        sut.getContactInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.password, "******19")
    }
    
    func testEmailUpdate() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.email = "mwai.dev@gmail.com"
        sut.updateEmail(userId: "M1001-B00")
        sut.email = "mwai.developer@gmail.com"
        sut.getContactInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.email, "mwai.dev@gmail.com")
    }
    
    func testStreetAddressUpdate() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.streetAddress = "1st first St"
        sut.updateStreetAddress(userId: "M1001-B00")
        sut.streetAddress = "2nd second St"
        sut.getBillingInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.streetAddress, "1st first St")
    }
    
    func testCityUpdate() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.city = "Chicago"
        sut.updateCity(userId: "M1001-B00")
        sut.city = "Schererville"
        sut.getBillingInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.city, "Chicago")
    }
    
    func testZipCodeUpdate() throws {
        sut.addUser(
            fullname: "Mwai Banda",
            phone: "2190000000",
            password: "*******",
            email: "mwai.developer@gmail.com",
            createdOn: "08/09/2022",
            userId: "M1001-B00"
        )
        sut.zipCode = "1200"
        sut.updateZipCode(userId: "M1001-B00")
        sut.zipCode = "0000"
        sut.getBillingInformation(userId: "M1001-B00")
        XCTAssertEqual(sut.zipCode, "1200")
    }
}
