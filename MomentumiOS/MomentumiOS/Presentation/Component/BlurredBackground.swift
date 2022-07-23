//
//  BlurredBackground.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI


struct BlurredBackground<Content: View>: View {
    var content: () -> (Content)
    var body: some View {
        ZStack {
            Color(.black)
                .opacity(0.2)
                .blur(radius: 200)
            RoundedRectangle(cornerRadius: 15)
                .fill(.white)
                .opacity(0.1)
                .background(
                    Color.white
                        .opacity(0.08)
                        .blur(radius: 10)
                )
                .background(
                    RoundedRectangle(cornerRadius: 15)
                        .stroke(
                            .linearGradient(colors: [
                                Color(hex: Constants.momentumOrange),
                                Color(hex: Constants.momentumOrange).opacity(0.5),
                                Color(.white).opacity(0.2),
                                .clear,
                                Color(.white).opacity(0.2)
                            ], startPoint: .topLeading, endPoint: .bottomTrailing),
                            lineWidth: 3
                        )
                        .padding(1)
                )
                .shadow(color: .black.opacity(0.1), radius: 5, x: -5, y: -5)
                .shadow(color: .black.opacity(0.1), radius: 5, x: 5, y: 5)

            VStack {
                content()
            }
        }
    }
}
