//
//  AuthControllerView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct AuthControllerView: View {
    @Environment(\.presentationMode) private var presentationMode
    var body: some View {
        VStack {
            HStack {
                Spacer()
                Button { presentationMode.wrappedValue.dismiss() } label: {
                    Image(systemName: "xmark.circle.fill")
                        .imageScale(.large)
                        .foregroundColor(.white)
                }
                
            }.padding()
            Spacer()
            BlurredBackground {
                SignUpView {
                    presentationMode.wrappedValue.dismiss()
                }
            }.frame(maxWidth: screenBounds.width - 100, maxHeight: 450)
            Spacer()
            
            HStack(alignment: .lastTextBaseline) {
                Spacer()
                Text("Already have an account? ")
                    .font(Font.headline.weight(.light)) +
                Text("Sign In here")
                    .fontWeight(.heavy)
                    .foregroundColor(Color(hex: Constants.momentumOrange))
                Image(systemName: "hand.tap")
                    .font(Font.headline.weight(.light))
                Spacer()
            }
            .font(.subheadline)
            .foregroundColor(Color.white)
            .padding()
        }
    }
}

struct AuthControllerView_Previews: PreviewProvider {
    static var previews: some View {
        AuthControllerView()
    }
}
