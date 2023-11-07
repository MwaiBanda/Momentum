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
    @EnvironmentObject var session: Session
    @State private var messages = [Message]()
    var body: some View {
        ScrollView {
            Divider()
            HStack {
            Text(MultiplatformConstants.shared.MESSAGES_SUBHEADING.uppercased())
                .font(.caption)
                .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                .padding(.leading)
                Spacer()
            }
            .padding(.top, 5)
            ForEach(messages, id: \.id) { message in
                NavigationLink {
                    MessageDetailView(message: message)
                } label: {
                    MessageCard(message: message)
                }

            }
        }.navigationTitle("Messages")
        .onAppear {
            messageViewModel.getAllMessages(userId: session.currentUser?.id ?? "") { messages in
                self.messages = messages
            }
        }
    }
}

#Preview {
    MessageView()
}
