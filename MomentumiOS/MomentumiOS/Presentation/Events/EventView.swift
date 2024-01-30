//
//  EventView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 11/23/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK

struct EventView: View {
    @StateObject private var eventViewModel = EventViewModel()
    @State private var groupedEvents = [GroupedEvent]()
    var body: some View {
        VStack {
            Divider()
            HStack {
                Text(MultiplatformConstants.shared.EVENTS_SUBHEADING.uppercased())
                    .font(.caption)
                    .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                    .padding(.leading)
                Spacer()
            }
            .padding(.top, 5)
            ScrollView {
                LazyVStack(alignment: .center, spacing: 10, pinnedViews: [.sectionHeaders], content: {
                    if groupedEvents.isEmpty {
                        ForEach(0..<12, id: \.self
                        ) { _ in
                            Section(header: HStack {
                                Text("placeholder")
                                .font(.title)
                                .fontWeight(.bold)
                                .padding(.bottom, 5)
                                Spacer()
                            }.padding(.leading).background(Color.white)) {
                                GroupedEvents(group: GroupedEvent(
                                    monthAndYear: "placeholder",
                                    events: Array(
                                        repeating: Event(
                                            id: "placeholder",
                                            startTime: "placeholder",
                                            endTime: "placeholder",
                                            description: "placeholder",
                                            intervalSummary: "placeholder",
                                            name: "placeholder",
                                            thumbnail: "placeholder"
                                        ),
                                        count: 4
                                 )))
                            }
                        }
                    } else {
                        ForEach(groupedEvents, id: \.self) { count in
                            Section(header: HStack {
                               Text(count.monthAndYear)
                                .font(.title)
                                .fontWeight(.bold)
                                .padding(.bottom, 5)
                                Spacer()
                            }.padding(.leading).background(Color.white)) {
                                GroupedEvents(group: count)
                            }
                        }
                    }
                })
            }
            .redacted(reason: groupedEvents.isEmpty ? .placeholder : [])
            .onAppear {
                DispatchQueue.global().async {
                    eventViewModel.getEvents { events in
                        DispatchQueue.main.async {
                            self.groupedEvents = events
                        }
                    }
                }
            }
        }
        .padding(.bottom, 10)
        .toolbar(content: {
            
            ToolbarItemGroup(placement: .navigationBarLeading) {
                Text("Events")
                    .font(.largeTitle)
                    .bold()
            }
            
//            ToolbarItemGroup(placement: .navigationBarTrailing) {
//                HStack {
//                    Button {
//
//                    } label: {
//                        Image(systemName: "magnifyingglass")
//                    }
//                    Button {

//                    } label: {
//                        Image(systemName: "line.3.horizontal")
//                    }
//                }
//            }
        })
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            DispatchQueue.global().async {
                eventViewModel.getEvents { events in
                    DispatchQueue.main.async {
                        self.groupedEvents = events
                    }
                }
            }
        }
    }
}

struct GroupedEvents: View {
    let group: GroupedEvent
    var body: some View {
        ForEach(group.events, id: \.id) { event in
            Card {
                HStack {
                  
                    VStack(alignment: .leading) {
                        Text(event.getFormattedStartDate())
                            .font(.subheadline)
                            .bold()
                            .lineLimit(1)
                            .multilineTextAlignment(.leading)
                        
                        
                        Text(event.name)
                            .font(.headline)
                            .fontWeight(.bold)
                            .lineLimit(1)
                            .foregroundColor(Color.init(hex: Constants.MOMENTUM_ORANGE))
                            .multilineTextAlignment(.leading)
                        
                        
                        Text(event.intervalSummary)
                            .foregroundColor(.gray)
                            .font(.caption)
                            .lineLimit(1)
                            .multilineTextAlignment(.leading)
                        
                        
                      
                    }.padding()
                    Spacer()
                    Text(event.getDisplayEventTime())
                        .foregroundColor(Color.gray)
                        .padding(.horizontal, 10)
                }
                .foregroundColor(Color.black)
            }
            .frame(maxHeight: 88)
            .padding(.horizontal)
            .padding(.top, 5)
        }
    }
}

#Preview {
    EventView()
}
