//
//  SessionTests.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/9/22.
//  Copyright © 2022 Momentum. All rights reserved.
//
@testable import TinyDi
@testable import MomentumiOS
import XCTest

//class SessionTests: BaseXCTestCase {
//    @Inject private var sut: Session
//    
//    override func setUpWithError() throws {
//        try super.setUpWithError()
//    }
//
//    override func tearDownWithError() throws {
//        try super.tearDownWithError()
//    }
//
//    func testCheckUser() throws {
////        XCTAssertNil(sut.currentUser)
//        sut.checkAndSignInAsGuest()
////        XCTAssertEqual(sut.currentUser?.isGuest, false)
//    }
//    
//    func testLogin() {
////        XCTAssertNil(sut.currentUser)
//        sut.signIn(email: "admin@test.com", password: "*******")
////        XCTAssertEqual(sut.currentUser?.email, "admin@test.com")
////        XCTAssertEqual(sut.currentUser?.isGuest, false)
//
//    }
//    
//    func testSignUp() {
////        XCTAssertNil(sut.currentUser)
//        sut.signUp(email: "test@admin.com", password: "*******")
////        XCTAssertEqual(sut.currentUser?.email, "test@admin.com")
////        XCTAssertEqual(sut.currentUser?.isGuest, false)
//    }
//    func testSignInAsGuest() throws {
////        XCTAssertNil(sut.currentUser)
//        sut.signInAsGuest()
////        XCTAssertEqual(sut.currentUser?.isGuest, true)
//    }
//    
//    func testSignOut() throws {
////        XCTAssertNil(sut.currentUser)
//        sut.signInAsGuest()
////        XCTAssertNotNil(sut.currentUser)
//        sut.signOut()
////        XCTAssertNil(sut.currentUser)
//    }
//}
