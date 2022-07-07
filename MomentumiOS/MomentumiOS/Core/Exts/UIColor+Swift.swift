//
//  UIColor+Swift.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit

extension UIColor {
    public convenience init?(hex: Int) {
        guard hex <= 0xffffffff else { return nil }

        let r = CGFloat((hex & 0xff000000) >> 24) / 255
        let g = CGFloat((hex & 0x00ff0000) >> 16) / 255
        let b = CGFloat((hex & 0x0000ff00) >> 8) / 255
        let a = CGFloat(hex & 0x000000ff) / 255

        self.init(red: r, green: g, blue: b, alpha: a)
    }
}
