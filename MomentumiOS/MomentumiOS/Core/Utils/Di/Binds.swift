//
//  Providing.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/26/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

@propertyWrapper
struct Binds<T> {
    var wrappedValue: T
    var named: String
    
    init(wrappedValue: T, named: String = "") {
        self.wrappedValue = wrappedValue
        self.named = named
        Resolver.inject(dependency: self.wrappedValue, named: named)
    }
}


