//
//  SermonsView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 9/19/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI

struct SermonsView: View {
    @StateObject private var sermonViewmodel = SermonsViewModel()
    var body: some View {
        Text("Hello, World!")
            .onAppear {
                sermonViewmodel.fetch()
            }
    }
}

struct SermonsView_Previews: PreviewProvider {
    static var previews: some View {
        SermonsView()
    }
}
