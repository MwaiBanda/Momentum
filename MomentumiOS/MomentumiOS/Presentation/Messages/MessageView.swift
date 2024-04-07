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
import Combine

struct MessageView: View {
    @StateObject private var messageViewModel = MessageViewModel()
    @EnvironmentObject var session: Session
    @State private var messages = [MessageGroup]()
    @State private var showSearch = false
    @State private var showFilter = false
    @State private var disposables = Set<AnyCancellable>()
    @State private var timerDisposables = Set<AnyCancellable>()
    var body: some View {
        VStack {
            Divider()
            HStack {
                Text(MultiplatformConstants.shared.MESSAGES_SUBHEADING.uppercased())
                    .font(.caption)
                    .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                    .padding(.leading)
                Spacer()
            }
            .padding(.top, 5)
            if #available(iOS 15, *) {
                MessageList(messageViewModel: messageViewModel, messages: $messages, showSearch: $showSearch, showFilter: $showFilter)
                    .refreshable {
                        messages = []
                        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5, execute: {
                            messageViewModel.getAllMessages(userId: session.currentUser?.id ?? "", isRefreshing: true)
                        })
                    }
            } else {
                MessageList(messageViewModel: messageViewModel, messages: $messages, showSearch: $showSearch, showFilter: $showFilter)
            }
        }
        .padding(.bottom, 10)
        .toolbar(content: {
            
            ToolbarItemGroup(placement: .navigationBarLeading) {
                Text("Messages")
                    .font(.largeTitle)
                    .bold()
            }
            
            ToolbarItemGroup(placement: .navigationBarTrailing) {
                HStack {
                    Button {
                        withAnimation(.easeInOut(duration: 0.35)) {
                            showSearch.toggle()
                            showFilter = false
                            
                        }
                        
                    } label: {
                        Image(systemName: showSearch ? "xmark" : "magnifyingglass")
                    }
                    Button {
                        withAnimation(.easeInOut(duration: 0.35)) {
                            showFilter.toggle()
                            showSearch = false
                        }
                    } label: {
                        Image(systemName: showFilter ? "xmark" : "line.3.horizontal")
                    }
                }
            }
        })
        .background(Color.white.edgesIgnoringSafeArea(.all))
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            if messages.isEmpty {
                messageViewModel.getAllMessages(userId: session.currentUser?.id ?? "")
                Timer.publish(every: 2.5, on: .main, in: .common)
                    .autoconnect()
                    .prepend(Date())
                    .map { _ in }
                    .sink(receiveValue: { _ in
                        withAnimation {
                            messageViewModel.shiftSearchTag()
                        }
                    })
                    .store(in: &timerDisposables)
                
                messageViewModel.filtered.sink { msgs in
                    print("sink>>>")
                    DispatchQueue.main.async {
                        messages = msgs
                        print(messages.flatMap({ $0.messages }).flatMap({ $0.passages }).compactMap({ $0.notes }))
                    }
                }.store(in: &disposables)
            }
        }
    }
}

struct MessageList: View {    
    @ObservedObject var messageViewModel: MessageViewModel
    @Binding var messages: [MessageGroup]
    @Binding var showSearch: Bool
    @Binding var showFilter: Bool
    var body: some View {
        ScrollView(showsIndicators: false) {
            VStack(alignment: .leading) {
                VStack(alignment: .leading) {
                    
                    if showSearch {
                        TextField("Search \(messageViewModel.searchTag)", text: $messageViewModel.searchTerm)
                            .padding(.top, 5)
                        
                    }
                    if showFilter {
                        ScrollView {
                            ForEach(messageViewModel.series, id: \.self) { series  in
                                Button{
                                    messageViewModel.filteredSeries = (series == messageViewModel.filteredSeries ? "" : series)
                                } label: {
                                    HStack {
                                        Image(systemName:  messageViewModel.filteredSeries == series ? "checkmark.square.fill" : "square")
                                            .imageScale(.medium)
                                            .foregroundColor(messageViewModel.filteredSeries == series  ? .init(hex: Constants.MOMENTUM_ORANGE) : .black)
                                        Text(series)
                                            .foregroundColor(.black)
                                        Spacer()
                                    }
                                }.contentShape(Rectangle())
                            }
                        }
                        
                    }
                }
                .font(.title3)
                .padding(.horizontal)
                .padding(.horizontal, 5)
                if showSearch || showFilter {
                    Divider()
                        .padding(.top, 5)
                }
            }
            .frame(height: showSearch ? 50 : showFilter ? 130 : 0)
            .padding(.bottom, 5)
            LazyVStack(alignment: .center, spacing: 10, pinnedViews: [.sectionHeaders]) {
                if messages.isEmpty {
                    ForEach(0..<12, id: \.self
                    ) { _ in
                        MessageCard(
                            isRedacted: true,
                            message: Message(
                                id: "1001",
                                thumbnail:"thumbnail",
                                series: "placeholder",
                                title: "placeholder",
                                preacher: "placeholder",
                                date: "placeholder",
                                createdOn: "placeholder",
                                passages: [Passage]()
                            ))
                    }
                } else {
                    ForEach(messages, id: \.self) { group in
                        Section(header: HStack {
                            Text(group.series)
                             .font(.title)
                             .fontWeight(.bold)
                             .padding(.bottom, 5)
                             Spacer()
                         }.padding(.leading).background(Color.white)){
                             Messages(messages: group.messages, messageViewModel: messageViewModel)
                        }
                    }
                }
            }
        }
        .redacted(reason: messages.isEmpty ? .placeholder : [])
    }
}

struct Messages: View {
    let messages: [Message]
    @ObservedObject var messageViewModel: MessageViewModel
    var body: some View {
        ForEach(messages, id: \.id) { message in
            NavigationLink {
                MessageDetailView(message: message, messageViewModel: messageViewModel)
            } label: {
                MessageCard(message: message)
            }.simultaneousGesture(
                TapGesture()
                    .onEnded { _ in
                        messageViewModel.passages = messageViewModel.messages.filter({ $0.messages.contains(where: { $0.id == message.id })}).first?.messages.first(where: { $0.id == message.id })?.passages ?? []
    //                                            print(message.passages.compactMap({ $0.notes }))
    //                                            print(messageViewModel.passages.compactMap({ $0.notes }))
                    }
            )
        }
    }

}
#Preview {
    MessageView()
}
