//
//  Resolving.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/26/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

protocol Resolving {
    static func resolve<T>() -> T
    static func register(context: (Resolving) -> Void)
    func resolve<T>() -> T
    func inject<T>(_ dependency: T)
}
