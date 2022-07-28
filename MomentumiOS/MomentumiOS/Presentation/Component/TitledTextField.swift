//
//  TitledTextField.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 7/25/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct TitledTextField: View {
    @State private var isTyping = false
    @State private var textHidden = ""
    var title: String
    @Binding var text: String
    var onCommit: () -> Void
  
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(title)
                .foregroundColor(.gray)
                .textCase(.uppercase )
                .font(.subheadline)
            ZStack(alignment: .center) {
                
                
                TextField(
                    "***",
                    text: (title == "password" && !isTyping) ? $textHidden : $text,
                    onCommit: {
                        isTyping = false
                        onCommit()
                    }
                )
                .simultaneousGesture(TapGesture().onEnded({
                    isTyping = true
                }))
                .font(text.isEmpty ? Font.body : Font.headline.weight(.bold))
                
                
                
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
        .onAppear {
            text.forEach { ch in
                if textHidden.count < text.count - 3 {
                    textHidden.append("*")
                } else {
                    textHidden.append(ch)
                }
            }
        }
    }
}

struct TitledTextField_Previews: PreviewProvider {
    static var previews: some View {
        TitledTextField(title: "fullname", text: .constant("")) {
            
        }
    }
}
