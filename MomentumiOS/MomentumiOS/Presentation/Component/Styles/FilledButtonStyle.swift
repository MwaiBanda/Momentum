//
//  FilledButtonStyle.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI

struct FilledButtonStyle: ButtonStyle {
    var isDisabled: Binding<Bool>
    init(isDisabled: Binding<Bool> = .constant(false)) {
        self.isDisabled = isDisabled
    }
    
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .font(.headline)
            .foregroundColor(.white)
            .background(Color(hex: Constants.MOMENTUM_ORANGE).opacity(isDisabled.wrappedValue ? 0.5 : 1))
            .cornerRadius(10)
    }
}

