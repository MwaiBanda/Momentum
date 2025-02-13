//
//  SignInView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/26/22.
//  Copyright © 2022 Momentum. All rights reserved.
//

import SwiftUI

struct SignInView: View {
    @Binding var showResetPassword: Bool
    @Binding var showToast: Bool
    @State private var email = ""
    @State private var password = ""
    @EnvironmentObject var session: Session
    var namespace: Namespace.ID
    var width: CGFloat
    var onSignInCompletion: () -> Void

    var body: some View {
        VStack(alignment: .leading) {
            VStack(alignment: .leading) {
                Text(showResetPassword ? "Reset Password" : "Sign In")
                .font(.title3)
                .fontWeight(.heavy)
                .foregroundColor(.white)
                .padding(.horizontal)
                .padding(.top)
                Text(showResetPassword ? "Enter Email to Get a Password Reset Link" : "Sing In To Your Account Proceed")
                .font(.subheadline)
                .foregroundColor(.white.opacity(0.3))
                .padding(.horizontal)
                .padding(.bottom, 5)
            Divider()
                .overlay(Color(.white).opacity(0.5))
                .padding(.bottom)
            }.matchedGeometryEffect(id: "title", in: namespace)
            TextField("", text: $email)
                .padding(.leading, 24)
                .placeholder(when: email.isEmpty) {
                    Text("Email")
                        .font(.headline)
                        .fontWeight(.regular)
                        .foregroundColor(.white)
                        .padding(.leading, 24)
                    
                }
                .foregroundColor(.white)
                .overlay(
                    HStack{
                        Image(systemName: "envelope")
                        Spacer()
                    }
                        .foregroundColor(Color.white)
                    
                )
                .textContentType(.emailAddress)
                .disableAutocorrection(true)
                .autocapitalization(.none)
                .ignoresSafeArea(.keyboard, edges: .bottom)
                .padding(.horizontal)
                .matchedGeometryEffect(id: "email", in: namespace)
            Divider()
                .overlay(Color(.white).opacity(0.5))
                .padding(.vertical)
            
            if !showResetPassword {
                PasswordTextfield(password: $password, placeholder: "Password") {
                    
                }
                .padding(.horizontal)
                .matchedGeometryEffect(id: "password", in: namespace)
                Divider()
                    .overlay(Color(.white).opacity(0.5))
                    .padding(.top)
            }
            Spacer()
            HStack {
                Spacer()
                Button {
                    if !email.isEmpty  {
                        if showResetPassword {
                            session.resetPassword(email: email) {
                                showToast.toggle()
                                let softImpact = UIImpactFeedbackGenerator(style: .soft)
                                softImpact.impactOccurred(intensity: 0.8)
                            }
                        } else  {
                            if  !password.isEmpty {
                                session.signIn(email: email, password: password) {
                                    onSignInCompletion()
                                }
                            }
                        }
                    }
                } label: {
                    Text("Confirm")
                        .fontWeight(.heavy)
                        .frame(width: width - 20, height: 55)
                }
                .buttonStyle(FilledButtonStyle())
                .padding(.bottom, 10)
                Spacer()
            }.matchedGeometryEffect(id: "button", in: namespace)
        }
        .accentColor(Color(hex: Constants.MOMENTUM_ORANGE))
      
    }
}


