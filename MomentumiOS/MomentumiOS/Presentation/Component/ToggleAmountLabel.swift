//
//  ToggleAmountLabel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/12/22.
//  Copyright © 2022 Mwai Banda. All rights reserved.
//

import SwiftUI

struct ToggleAmountLabel: View {
    var title: String
    @Binding var amount: String
    var initialAmount: String = "0"
    @Binding var isSelected: Bool
    var showLabel: Bool
    var onToggleLabel: (Bool) -> Void
    var onAmountChanged: (String) -> Void
    var body: some View {
        HStack(alignment: .center) {
            HStack {
                Circle()
                    .fill(isSelected ? Color(hex: Constants.MOMENTUM_ORANGE) : .gray)
                    .frame(width: 15, height: 15)
                    .padding(5)
                    .overlay(
                        Circle()
                            .strokeBorder(isSelected ? Color(hex: Constants.MOMENTUM_ORANGE) : .gray, lineWidth: 3)
                            .frame(width: 25, height: 25)
                    )
                Text(title)
                    .font(.headline)
                    .fontWeight(.heavy)
                
            }
            .onTapGesture {
                isSelected.toggle()
                onToggleLabel(isSelected)
            }
            Spacer(minLength: 0)
            HStack(spacing: 0) {
                Text("amount: ")
                    .foregroundColor(.gray)
                    .font(.subheadline)
                    .bold()
                Text("$")
                    .font(.headline)
                    .bold()
                TextField("", text: $amount, onEditingChanged: { isTyping in
                    onAmountChanged(amount)
                    Log.d(tag: "ToggleLabel/Amount", message: "Amount changed \(title):\(amount)")
                })
                .font(.headline)
                .textContentType(.oneTimeCode)
                .keyboardType(.numberPad)
                .fixedSize()
                .frame(minWidth: 30, alignment: .leading)
                .textFieldFocusableArea()
                .disabled(!isSelected)
            }.opacity(showLabel ? 1 : 0)
            
        }
        .padding()
        .onAppear {
            amount = initialAmount
            
        }
    
    }
    func commitAmountChanged() {
        onAmountChanged(amount)
    }
}

