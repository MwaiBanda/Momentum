//
//  PaymentResultView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI
import Stripe

struct PaymentResultView: View {
    let result: PaymentSheetResult
    var body: some View {
        switch result {
        case .completed:
            PaymentSuccessView()
        case .canceled:
            EmptyView()
        case .failed(let error):
            PaymentFailureView()
                .onAppear {
                    Log.d(tag:"[Failed]", message: error.localizedDescription)
                }
        }
    }
}

struct PaymentResultView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentResultView(result: .completed)
    }
}
