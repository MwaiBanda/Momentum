//
//  Resolver.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/21/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation



final class Resolver {
    private var dependencies = [String: AnyObject]()
    private static let shared = Resolver()
    
    static func inject<T>(dependency: T){
        shared.inject(dependency)
    }
    
    static func resolve<T>() -> T {
        shared.resolve()
    }
    static func register(context: (Resolver) -> Void) {
        context(shared)
    }
    
    internal func inject<T>(_ dependency: T) {
        let key = String(describing: T.self)
        dependencies[key] = dependency as AnyObject
    }
    
   
    internal func resolve<T>() -> T {
        let key = String(describing: T.self)
        let dependency = dependencies[key] as? T
        assert(dependency != nil, "No dependency found of \(key)")
        return dependency!
    }
    private init() { }
}
