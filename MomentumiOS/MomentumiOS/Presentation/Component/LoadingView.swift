//
//  LoadingView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        VStack {
            Spacer()
            ProgressView()
                .progressViewStyle(
                    CircularProgressViewStyle(
                        tint: Color(
                            hex: Constants.momentumOrange
                        )
                    )
                )
            Spacer()
        }
    }
}

struct LoadingView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView()
    }
}
