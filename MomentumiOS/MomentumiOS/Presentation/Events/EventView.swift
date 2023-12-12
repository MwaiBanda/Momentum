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
                if groupedEvents.isEmpty {
                    ForEach(0..<12, id: \.self
                    ) { _ in
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
                } else {
                    ForEach(groupedEvents, id: \.id) { group in
                        GroupedEvents(group: group)
                    }
                }
            }.redacted(reason: groupedEvents.isEmpty ? .placeholder : [])
        }
        
        .navigationTitle("Events")
        .onAppear {
            DispatchQueue.main.async {
                eventViewModel.getEvents { events in
                    self.groupedEvents = events
                }
            }
        }
    }
}

struct GroupedEvents: View {
    let group: GroupedEvent
    var body: some View {
        HStack {
            Text(group.monthAndYear)
                .font(.title)
                .fontWeight(.bold)
            Spacer()
        }.padding(.leading)

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
