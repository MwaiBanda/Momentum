//
//  MealDetailView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/8/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK

struct VolunteerServiceDetailView: View {
    @ObservedObject var profileViewModel: ProfileViewModel
    @EnvironmentObject var session: Session
    
    @State private var showAuthSheet = false
    @State private var showVoluteerSheet = false
    @State private var notes = "\u{2022} "
    @State private var selectedIndex = 0
    @State private var days = [Day]()
    var service: VolunteerService
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                Card {
                    HStack {
                        VStack(alignment: .leading) {
                            Text("Organizer")
                                .font(.title2)
                                .bold()
                            RecipentInfo(
                                title: "Team Lead",
                                description: service.organizer,
                                icon: "exclamationmark.shield"
                            )
                            RecipentInfo(
                                title: "Email",
                                description: service.email,
                                icon: "person.3"
                            )
                            RecipentInfo(
                                title: "Description",
                                description: service.description_,
                                icon: "deskclock"
                            )
                        }
                        Spacer()
                    }.padding(10)
                    
                }
                Text("Calendar")
                    .font(.title2)
                    .bold()
                
                ForEach(Array(days.enumerated()), id: \.offset) { i, m in
                    Card {
                        HStack {
                            VStack(alignment: .leading) {
                                Text(getDate(meal: m).split(separator: ",").first?.trimmingCharacters(in: .whitespacesAndNewlines) ?? "")
                                    .bold()
                                Text(getDate(meal: m).split(separator: ",").last?.trimmingCharacters(in: .whitespacesAndNewlines) ?? "")
                            }.padding(10)
                            Divider()
                                .padding(.vertical, 5)
                            if !m.notes.isEmpty {
                                ZStack(alignment: .center) {
                                    
                                    Circle()
                                        .fill(Color(hex: Constants.MOMENTUM_ORANGE))
                                        .frame(width: 50, height: 50)
                                    
                                    
                                    Text((m.user?.fullname ?? "")
                                        .split(separator: " ")
                                        .map({ String($0.first?.uppercased() ?? "")})
                                        .reduce("", { x, y in x + y})
                                    )
                                    .font(.title2)
                                    .fontWeight(.heavy)
                                    .foregroundColor(.white)
                                }.padding(.vertical, 10)

                            }
                            VStack(alignment: .leading) {
                                if m.notes.isEmpty {
                                    Text("Available")
                                        .bold()
                                    Button {
                                        if (session.currentUser?.isGuest ?? true) {
                                            showAuthSheet.toggle()
                                        } else {
                                            selectedIndex = i
                                            showVoluteerSheet.toggle()
                                        }
                                    } label: {
                                        Text("Volunteer")
                                            .foregroundColor(.white)
                                            .padding(.horizontal, 15)
                                            .padding(.vertical, 8)
                                            .frame(maxWidth: .infinity)
                                            .background(Color(hex: Constants.MOMENTUM_ORANGE))
                                            .clipShape(RoundedCorner(radius: 10))
                                    }
                                } else {
                                    Text(m.user?.fullname ?? "")
                                        .bold()
                                    Text(m.notes.trimmingCharacters(in: .whitespacesAndNewlines))
                                }
                                
                            }.padding(10)
                            Spacer()
                        }
                    }
                    .padding(.top, 5)
                }
            }
            .padding(.horizontal, 10)
        }
        .sheet(isPresented: $showAuthSheet) {
            MomentumBlurredBackground {
                AuthControllerView()
            }
        }
        .onAppear {
            days = service.days.sorted(by: { getDay(meal: $0) < getDay(meal: $1) })
        }
    }
    func getDate(meal: Day) -> String {
        _ = "2016-04-14T10:44:00+0000"
        
        let dateFormatter = DateFormatter()
        dateFormatter.locale = Locale(identifier: "en_US_POSIX") // set locale to reliable US_POSIX
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ssZ"
        let date = dateFormatter.date(from: meal.date) ?? Date()
        
        dateFormatter.dateFormat = "MMM d, EEE"
        return dateFormatter.string(from: date)
    }
    
    func getDay(meal: Day) -> Int {
        return Int(getDate(meal: meal).split(separator: ",").first?.split(separator: " ").last ?? "0") ?? 0
    }
}

