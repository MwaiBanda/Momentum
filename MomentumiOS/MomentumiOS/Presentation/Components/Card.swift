//
//  Card.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/7/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI

struct Card<Content: View>: View {
    var content: () -> (Content)
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 10, style: .continuous)
                .fill(.white)
                .shadow(radius: 1)
            VStack(alignment: .leading ){
                content()
            }
            .clipShape(RoundedRectangle(cornerRadius: 10, style: .continuous))
        }
    }
}


struct Card_Previews: PreviewProvider {
    static var previews: some View {
        Card {
            Text("Card")
        }
    }
}
