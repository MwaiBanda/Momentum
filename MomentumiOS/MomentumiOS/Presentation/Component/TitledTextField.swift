//
//  TitledTextField.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/25/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct TitledTextField: View {
    var title: String
    var text: Binding<String>
    var onCommit: () -> Void
    @State private var isTyping = false
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(title)
                .foregroundColor(.gray)
                .textCase(.uppercase )
                .font(.subheadline)
            ZStack(alignment: .center) {
                TextField("***", text: text, onCommit: { isTyping = false })
                    .simultaneousGesture(TapGesture().onEnded({
                        isTyping = true
                    }))
                    .font(text.wrappedValue.isEmpty ? Font.body : Font.headline.weight(.bold))
                HStack  {
                    Spacer()
                    Image(systemName: isTyping ? "keyboard.badge.ellipsis" : "square.and.pencil")
                        .imageScale(.medium)
                        .foregroundColor(Color(hex: Constants.momentumOrange))
                        .offset(y: -10)
                }
            }
        }
        .padding(.horizontal)
        .padding(.vertical, 10)
    }
}

struct TitledTextField_Previews: PreviewProvider {
    static var previews: some View {
        TitledTextField(title: "fullname", text: .constant("")) {
            
        }
    }
}
