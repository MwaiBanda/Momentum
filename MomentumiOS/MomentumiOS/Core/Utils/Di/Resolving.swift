//
//  Resolving.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/26/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

protocol Resolving {
    static func resolve<T>(named: String) -> T
    static func inject<T>(dependency: T, named: String)
    static func register(context: (Resolver) -> Void)
    static func clear(onCompletion: @escaping () -> Void)
    static func release<T>(_ dependency: T)
    func resolve<T>(named: String) -> T
    func inject<T>(_ dependency: T, named: String)
    func release<T>(_ dependency: T)
    func clear(onCompletion: @escaping () -> Void)
}
