//
//  MessageCard.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/7/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK
import SDWebImageSwiftUI

struct MessageCard: View {
    let message: Message
    var body: some View {
        Card {
            HStack {
                WebImage(url: URL(string: message.thumbnail))
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(maxWidth: 150, maxHeight: 150)
                VStack(alignment: .leading) {
                    Text(message.series)
                        .font(.caption)
                        .lineLimit(1)
                        .multilineTextAlignment(.leading)
                    
                    
                    Text(message.title)
                        .font(.subheadline)
                        .bold()
                        .lineLimit(1)
                        .multilineTextAlignment(.leading)
                    
                    
                    Text(message.preacher)
                        .foregroundColor(.gray)
                        .font(.caption)
                        .lineLimit(1)
                        .multilineTextAlignment(.leading)
                    
                    
                    Text(message.date)
                        .font(.caption2)
                        .multilineTextAlignment(.leading)
                }
                Spacer()
            }
            .foregroundColor(Color.black)
        }
        .frame(maxHeight: 88)
        .padding(.horizontal, 10)
        .padding(.top, 5)
    }
}


