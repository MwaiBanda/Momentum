//
//  MessageView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/5/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK
import SDWebImageSwiftUI

struct MessageView: View {
    @StateObject private var messageViewModel = MessageViewModel()
    @State private var messages = [Message]()
    var body: some View {
        ForEach(messages, id: \.id) { message in
            Card {
                HStack {
                    WebImage(url: URL(string: message.thumbnail))
                        .resizable()
                        .aspectRatio(contentMode: .fill)
                        .frame(maxHeight: 150)
                        
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
                    .padding(5)
                    .padding(.bottom, 5)
                    Spacer()
                }
            }.onAppear {
                messageViewModel.getAllMessages(userId: "") { messages in
                    self.messages = messages
                }
            }
        }
    }
}

#Preview {
    MessageView()
}
