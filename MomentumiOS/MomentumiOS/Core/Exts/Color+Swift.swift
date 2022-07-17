//
//  Color+Swift.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/1/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI


extension Color {
    init(hex: Int, opacity: Double = 1.0) {
        let red = CGFloat((Float((hex & 0xff0000) >> 16)) / 255.0)
        let green = CGFloat((Float((hex & 0x00ff00) >> 8)) / 255.0)
        let blue = CGFloat((Float((hex & 0x0000ff) >> 0)) / 255.0)
        self.init(.sRGB, red: red, green: green, blue: blue, opacity: opacity)
    }
}
