//
//  FilledButtonStyle.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct FilledButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .font(.headline)
            .foregroundColor(.white)
            .background(Color(hex: Constants.momentumOrange))
            .cornerRadius(10)
    }
}

