//
//  Resolver.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/21/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation



final class Resolver: Resolving {
    
    private var dependencies = [String: AnyObject]()
    static let shared = Resolver()
    
    static func inject<T>(dependency: T, named: String){
        shared.inject(dependency, named: named)
    }
    
    static func resolve<T>(named: String) -> T {
        shared.resolve(named: named)
    }
    static func register(context: (Resolving) -> Void) {
        context(shared)
    }
    static func clear(onCompletion: @escaping () -> Void) {
        shared.clear(onCompletion: onCompletion)
    }
    static func release<T>(_ dependency: T, named: String = "") {
        shared.release(dependency, named: named)
    }
    internal func inject<T>(_ dependency: T, named: String = "") {
        let key = (named.isEmpty ? String(describing: T.self) : named)
        dependencies[key] = dependency as AnyObject
    }
    internal func resolve<T>() -> T {
        let key = String(describing: T.self)
        let dependency = dependencies[key] as? T
        assert(dependency != nil, "No dependency found of \(key)")
        return dependency!
    }
    
   
    internal func resolve<T>(named: String = "") -> T {
        let key = (named.isEmpty ? String(describing: T.self) : named)
        let dependency = dependencies[key] as? T
        assert(dependency != nil, "No dependency found of \(key)")
        return dependency!
    }
    
    internal func release<T>(_ dependency: T, named: String) {
        let key = (named.isEmpty ? String(describing: T.self) : named)
            .replacingOccurrences(of: ".Protocol", with: "")
        dependencies[key] = nil
        print("[AFTER RELEASE]: \(dependencies)")
    }
    
    internal func clear(onCompletion: @escaping () -> Void) {
        dependencies.removeAll()
        onCompletion()
    }
    private init() { }
}
