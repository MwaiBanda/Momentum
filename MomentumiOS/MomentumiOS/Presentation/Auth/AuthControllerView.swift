//
//  AuthControllerView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/22/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI
import AlertToast

struct AuthControllerView: View {
    @Namespace var namespace
    @Environment(\.presentationMode) private var presentationMode
    @State private var showSignUp = true
    @State private var showResetPassword = false
    @State private var showToast = false

    var body: some View {
        
        GeometryReader { proxy in
            VStack(alignment: .trailing) {
                if DeviceType.deviceIsPad {
                    Spacer()
                }
                
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
                                showResetPassword: $showResetPassword,
                                showToast: $showToast,
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
                    maxHeight: showSignUp ? 490 : showResetPassword ? 220 : 300
                )
                
                Spacer()
                VStack {
                    if !showSignUp && !showResetPassword {
                        Button {
                            withAnimation(.easeOut(duration: 0.35)){
                                showResetPassword = true
                            }
                        } label: {
                            HStack(alignment: .center) {
                                Spacer()
                                Text( "Forgot Password? ")
                                    .font(Font.subheadline.weight(.light))
                                Image(systemName: "hand.tap")
                                    .font(Font.subheadline.weight(.light))
                                Spacer()
                            }
                            .font(.caption)
                            .foregroundColor(Color.white)
                            .frame(width: DeviceType.deviceIsPad ? proxy.size.width - 250 : screenBounds.width - 90, height: 45)
                        }
                    }
                    
                    Button {
                        withAnimation(.easeOut(duration: 0.35)){
                            if showResetPassword {
                                showResetPassword = false
                            } else {
                                showSignUp.toggle()
                            }
                        }
                    } label: {
                        BlurredBackground {
                            HStack(alignment: .center) {
                                Spacer()
                                Text( "\(showSignUp || showResetPassword ? "Already" : "Don't") have an account? ")
                                    .font(Font.subheadline.weight(.light)) +
                                Text("\(showSignUp || showResetPassword  ? "Sign In" : "Sign Up") here")
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
                }
                if DeviceType.deviceIsPad {
                    Spacer()
                }
            }.frame(maxWidth: proxy.size.width, maxHeight: proxy.size.height)
        }
        .toast(isPresenting: $showToast){
            AlertToast(displayMode: .banner(.pop), type: .regular, title: "Email Sent!", subTitle: "Please check your email for a password reset link")
        }
        
        
    }
}

struct AuthControllerView_Previews: PreviewProvider {
    static var previews: some View {
        AuthControllerView()
    }
}

