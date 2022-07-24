//
//  PasswordTextfield.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/22/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PasswordTextfield: View {
    @State private var showPassword = false
    @Binding var password: String
    var placeholder: String
    var onCommit: () -> Void
    var body: some View {
        ZStack {
            if showPassword {
                TextField("" ,text: $password, onCommit: onCommit)
                    .placeholder(when: password.isEmpty) {
                           Text(placeholder)
                            .font(.headline)
                            .fontWeight(.regular)
                            .foregroundColor(.white)
                            .padding(.leading, 24)
                    }
                    .padding(.leading, 24)
                    .textContentType(.username)
                    .disableAutocorrection(true)
                    .keyboardType(.emailAddress)
                    .autocapitalization(.none)
                    .foregroundColor(Color.white)
                    .disableAutocorrection(false)
            } else {
                SecureField("", text: $password, onCommit: onCommit)
                    .foregroundColor(.white)
                    .padding(.leading, 24)
                    .placeholder(when: password.isEmpty) {
                           Text(placeholder)
                            .font(.headline)
                            .fontWeight(.regular)
                            .foregroundColor(.white)
                            .padding(.leading, 24)
                    }
                    
            }
            HStack {
                Spacer()
                Button(action: {
                    showPassword.toggle()
                }) {
                    Image(systemName: self.showPassword ? "eye" : "eye.slash" )
                        .foregroundColor(.white)
                }
                
            }
        }
        .overlay(
            HStack{
                Image(systemName: "lock")
                Spacer()
            }
            .foregroundColor(Color.white)
            
        )
        .ignoresSafeArea(.keyboard, edges: .bottom)
    }
}


