//
//  MomentumiOSTests.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import XCTest
@testable import MomentumiOS

class MomentumiOSTests: BaseXCTestCase {
    @Inject private var foo: FooProviding
    
    override func setUpWithError() throws {
        try super.setUpWithError()
  }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        _foo.release()
    }

    func testFoo() throws {
        XCTAssertFalse(foo.bar().isEmpty)
        XCTAssertEqual(foo.bar(), "Bar")
    }
}
