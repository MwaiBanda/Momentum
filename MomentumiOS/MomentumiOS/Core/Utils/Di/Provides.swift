//
//  Provides.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/27/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

@resultBuilder
struct Provides {
    static var named:String = ""
    
    init(named: String = ""){
        Provides.named = named
    }
    
    static func buildBlock<T>(_ dependencies: T...) -> Void {
        dependencies.forEach { dependency in
            Resolver.inject(dependency: dependency, named: named)
        }
    }
}
