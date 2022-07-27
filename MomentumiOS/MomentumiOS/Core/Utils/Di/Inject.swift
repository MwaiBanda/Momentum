//
//  Inject.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/21/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

@propertyWrapper
struct Inject<T> {
    var wrappedValue: T
    
    init() {
        wrappedValue = Resolver.resolve()
    }
}
