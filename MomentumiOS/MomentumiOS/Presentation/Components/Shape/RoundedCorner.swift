//
//  RoundedCorner.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/20/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI


struct RoundedCorner: Shape {

    var radius: CGFloat = .infinity
    var corners: UIRectCorner = .allCorners

    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(roundedRect: rect, byRoundingCorners: corners, cornerRadii: CGSize(width: radius, height: radius))
        return Path(path.cgPath)
    }
}
