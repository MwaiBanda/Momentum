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
    var named: String
    var wrappedValue: T {
        get {
            Resolver.resolve(named: named)
        }
    }
    init(named: String = ""){
        self.named = named
    }
    
    func release<T>(_ type: T) {
        Resolver.release(type)
    }
    func release() {
        Resolver.release(wrappedValue)
    }
    func release(named: String) {
        Resolver.release(wrappedValue, named: named)
    }
}

