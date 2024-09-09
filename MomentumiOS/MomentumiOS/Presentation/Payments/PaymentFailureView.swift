//
//  PaymentFailureView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI
import Lottie

struct PaymentFailureView: View {
    @Environment(\.presentationMode) private var presentationMode
    var body: some View {
        ZStack {
            Color.white.ignoresSafeArea()
        VStack {
            LottieView(animation: .named("failed"))
            .frame(width: 250, height: 250)
        Text("Payment Failed")
            .fontWeight(.heavy)
            .font(.system(size: 24))
            .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
            Text("""
                Your payment hasn't been made successfully.
                Try again to proceed with making a payment.
                """)
                .multilineTextAlignment(.center)
                .padding()
                .padding(.bottom)
            Spacer()
            Button {
                presentationMode.wrappedValue.dismiss()
            } label: {
                Text("Back")
                    .fontWeight(.heavy)
                    .frame(width: screenBounds.width - 30, height: 55)
            }.buttonStyle(FilledButtonStyle())
            Divider()
        }.navigationBarBackButtonHidden(true)
        }
    }
}

struct PaymentFailureView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentFailureView()
    }
}
