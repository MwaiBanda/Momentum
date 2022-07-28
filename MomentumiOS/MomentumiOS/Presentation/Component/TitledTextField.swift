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
    @Binding var text: String
    var onCommit: () -> Void
    @State private var isTyping = false
    var password: String {
        var returnStr = ""
        text.forEach { ch in
            
                returnStr.append("*")
           
        }
        return returnStr
    }
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(title)
                .foregroundColor(.gray)
                .textCase(.uppercase )
                .font(.subheadline)
            ZStack(alignment: .center) {
                
                if  title == "password" {
                    Text(password)
                        .font(Font.headline.weight(.bold))
                        .onTapGesture {
                            isTyping = true
                        }
               
                } else {
                    TextField("***", text: $text, onCommit: { isTyping = false })
                        .simultaneousGesture(TapGesture().onEnded({
                            isTyping = true
                        }))
                        .font(text.isEmpty ? Font.body : Font.headline.weight(.bold))
                }
                   
                
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
