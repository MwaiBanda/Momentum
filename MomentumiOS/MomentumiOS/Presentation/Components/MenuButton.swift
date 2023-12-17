//
//  MenuButton.swift
//  PeaceWork (iOS)
//
//  Created by Mwai Banda on 12/23/21.
//

import SwiftUI

struct MenuButton: View {
    var icon: String
    var title: String
    var onClick: () -> Void
    var body: some View {
        Button {
            onClick()
        } label: {
            HStack {
                Image(systemName: icon)
                    .font(.title3)
                Text(title)
                    .font(.title3)
                    .bold()
                Spacer()
            }
            .padding()
            .padding(.vertical, 5)
        }
    }
}

struct MenuButton_Previews: PreviewProvider {
    static var previews: some View {
        MenuButton(icon: "doc.text.magnifyingglass", title: "Find Jobs", onClick: {})
    }
}
