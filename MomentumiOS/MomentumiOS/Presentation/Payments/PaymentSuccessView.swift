//
//  PaymentSucessView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PaymentSuccessView: View {
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        ZStack {
            Color.white.ignoresSafeArea()
        VStack {
        LottieView(name: "success")
            .frame(width: 250, height: 250)
        Text("Payment Success")
            .fontWeight(.heavy)
            .font(.system(size: 24))
            .foregroundColor(Color(hex: Constants.momentumOrange))
            Text("""
                Your payment has been made successfully.
                For more details. Check the transactions
                tab to see or give again, in the Accounts
                section.
                """)
                .multilineTextAlignment(.center)
                .padding()
                .padding(.bottom)
            
            Button {
                presentationMode.wrappedValue.dismiss()
            } label: {
                Text("Back")
                    .fontWeight(.heavy)
                    .frame(width: screenBounds.width - 30, height: 55)
            }.buttonStyle(FilledButtonStyle())
            Spacer()
        }.navigationBarBackButtonHidden(true)
        }
    }
}

struct PaymentSuccessView_Previews: PreviewProvider {
    static var previews: some View {
        PaymentSuccessView()
    }
}
