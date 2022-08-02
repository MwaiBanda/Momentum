//
//  MomentumBaseXCTest.swift
//  MomentumiOSTests
//
//  Created by Mwai Banda on 8/1/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import XCTest
@testable import MomentumiOS

class BaseXCTestCase: XCTestCase {
    override func setUpWithError() throws {
        try super.setUpWithError()
        DiRegistry.shared.clear {
            DiRegistry.shared.injectTestDependencies()
            print(Resolver.shared.dependencies)
        }
        
    }
    
    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }
}
