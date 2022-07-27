//
//  Providing.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/26/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

@propertyWrapper
struct Providing<T> {
    var wrappedValue: T
    
    init(wrappedValue: T) {
        self.wrappedValue = wrappedValue
        Resolver.inject(dependency: self.wrappedValue)
    }
}

@resultBuilder
struct Provides {
    static func buildBlock<T>(_ dependencies: T...) -> Void {
        dependencies.forEach { dependency in
            Resolver.inject(dependency: dependency)
        }
    }
}

@resultBuilder
struct Module {
    static func buildBlock(_ components: () -> Void...) -> Void {
        components.forEach { function in
             function()
        }
    }
}
