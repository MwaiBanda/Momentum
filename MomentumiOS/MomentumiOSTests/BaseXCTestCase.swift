//
//  MomentumBaseXCTest.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/1/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import XCTest
@testable import MomentumiOS
@testable import TinyDi

class BaseXCTestCase: XCTestCase {
    override func setUpWithError() throws {
        try super.setUpWithError()
        DependencyRegistry.shared.clear {
            DependencyRegistry.shared.injectTestDependencies()
        }
    }
    
    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }
}
