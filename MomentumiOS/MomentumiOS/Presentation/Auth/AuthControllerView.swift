//
//  AuthControllerView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct AuthControllerView: View {
    @Namespace var namespace
    @Environment(\.presentationMode) private var presentationMode
    @State private var showSignUp = true
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
                Group {
                    if showSignUp {
                        SignUpView(namespace: namespace) {
                            presentationMode.wrappedValue.dismiss()
                        }
                        .transition(AnyTransition.asymmetric(insertion: .opacity, removal: .opacity))
                    } else {
                        SignInView(namespace: namespace) {
                            presentationMode.wrappedValue.dismiss()
                        }
                        .transition(AnyTransition.asymmetric(insertion: .opacity, removal: .opacity))
                    }
                }
            }
            .frame(maxWidth: screenBounds.width - 100, maxHeight: showSignUp ? 490 : 300)
            Spacer()
            Button {
                withAnimation(.easeOut(duration: 0.35)){
                    showSignUp.toggle()
                }
            } label: {
                BlurredBackground {
                HStack(alignment: .center) {
                    Spacer()
                    Text( "\(showSignUp ? "Already" : "Don't") have an account? ")
                        .font(Font.subheadline.weight(.light)) +
                    Text("\(showSignUp ? "Sign In" : "Sign Up") here")
                        .fontWeight(.heavy)
                        .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                    Image(systemName: "hand.tap")
                        .font(Font.subheadline.weight(.light))
                    Spacer()
                }
                .font(.caption)
                .foregroundColor(Color.white)
                }.frame(width:  screenBounds.width - 100, height: 45)
            }
        }
    }
}

struct AuthControllerView_Previews: PreviewProvider {
    static var previews: some View {
        AuthControllerView()
    }
}
