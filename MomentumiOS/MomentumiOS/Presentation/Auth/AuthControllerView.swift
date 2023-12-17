//
//  AuthControllerView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/22/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI

struct AuthControllerView: View {
    @Namespace var namespace
    @Environment(\.presentationMode) private var presentationMode
    @State private var showSignUp = true
    var body: some View {
        GeometryReader { proxy in
            VStack(alignment: .trailing) {
                if DeviceType.deviceIsPad {
                    Spacer()
                }
            
                    
                    Button { presentationMode.wrappedValue.dismiss() } label: {
                        Image(systemName: "xmark.circle.fill")
                            .imageScale(.large)
                            .foregroundColor(.white)
                    }
                    .padding(.top)
                    .offset(x: DeviceType.deviceIsPad ? 0 : 30)
            
                
                
                Spacer()
                BlurredBackground {
                    Group {
                        if showSignUp {
                            SignUpView(
                                namespace: namespace,
                                width: DeviceType.deviceIsPad ? proxy.size.width - 250: screenBounds.width - 90
                            ) {
                                presentationMode.wrappedValue.dismiss()
                            }
                            .transition(AnyTransition.asymmetric(insertion: .opacity, removal: .opacity))
                        } else {
                            SignInView(
                                namespace: namespace,
                                width: DeviceType.deviceIsPad ? proxy.size.width - 250 : screenBounds.width - 90
                            ) {
                                presentationMode.wrappedValue.dismiss()
                            }
                            .transition(AnyTransition.asymmetric(insertion: .opacity, removal: .opacity))
                        }
                    }
                }
                .frame(
                    maxWidth: DeviceType.deviceIsPad ? proxy.size.width - 250 : screenBounds.width - 90,
                    maxHeight: showSignUp ? 490 : 300
                )
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
                    }
                    .frame(width: DeviceType.deviceIsPad ? proxy.size.width - 250 : screenBounds.width - 90, height: 45)
                }.padding(.vertical, 10)
                if DeviceType.deviceIsPad {
                    Spacer()
                }
            }.frame(maxWidth: proxy.size.width)
        }
    }
}

struct AuthControllerView_Previews: PreviewProvider {
    static var previews: some View {
        AuthControllerView()
    }
}
