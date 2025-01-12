//
//  DefaultTextfield.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 1/11/25.
//  Copyright © 2025 Momentum. All rights reserved.
//
import SwiftUI


struct DefaultTextfield: View {
    var title: String
    var icon: String
    @Binding var text: String
    var body: some View {
        TextField(title, text: $text)
            .textContentType(.username)
            .disableAutocorrection(true)
            .keyboardType(.emailAddress)
            .autocapitalization(.none)
            .padding(.leading, 24)
            .padding(15)
            .foregroundColor(Color.black)
            .cornerRadius(7)
            .disableAutocorrection(false)
            .overlay(
                HStack{
                    Image(systemName: icon)
                    Spacer()
                }
                    .padding(.horizontal, 12)
                    .foregroundColor(Color.gray)
                
            )
            .ignoresSafeArea(.keyboard, edges: .bottom)
    }
}
