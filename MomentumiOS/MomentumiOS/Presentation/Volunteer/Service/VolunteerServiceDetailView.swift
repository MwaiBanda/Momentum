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
    @ObservedObject var servicesViewModel: ServicesViewModel
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
                                Text(getDate(day: m).split(separator: ",").first?.trimmingCharacters(in: .whitespacesAndNewlines) ?? "")
                                    .bold()
                                Text(getDate(day: m).split(separator: ",").last?.trimmingCharacters(in: .whitespacesAndNewlines) ?? "")
                            }.padding(10)
                            Divider()
                                .padding(.vertical, 5)
                            if !m.notes.isEmpty {
                                ZStack(alignment: .center) {
                                    
                                    Circle()
                                        .fill(Color(hex: Constants.MOMENTUM_ORANGE))
                                        .frame(width: 50, height: 50)
                                    
                                    
                                    Text(m.user.fullname
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
                                    Text(m.user.fullname)
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
        .sheet(isPresented: $showVoluteerSheet) {
            VStack {
                Spacer()
                ZStack {
                    RoundedRectangle(cornerRadius: 10, style: .continuous)
                        .fill(Color.white)
                        .shadow(color: Color.black.opacity(0.15), radius: 3)
                    
                    VStack(alignment: .leading) {
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Let's Get Started")
                                    .bold()
                                    .font(.title2)
                                    .foregroundColor(.gray)
                                Text("With this is new meal" )
                                    .font(.headline)
                                    .foregroundColor(.gray)
                                    .bold()
                            }
                            Spacer()
                            
                            
                        }.padding(.vertical)
                            .padding(.leading)
                            .padding(.top)
                        Group {
                            Divider()
                            VStack(alignment: .leading) {
                                Text("Date")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                Text(getDate(day: days[selectedIndex]))
                            }.padding(.leading, 15)
                            Divider()
                        }
                        VStack(alignment: .leading) {
                            Text("Addditional Notes")
                                .foregroundColor(.gray)
                                .font(.headline)
                                .fontWeight(.semibold)
                            TextEditor(text: $notes)
                                .frame(minHeight: 120, maxHeight: 120)
                                .foregroundColor(.black)
                                .onChange(of: notes) { [notes] newText in
                                    if newText.suffix(1) == "\n" && newText > notes {
                                        self.notes.append("\u{2022} ")
                                    }
                                }
                        }.padding(.horizontal, 15)
                        Spacer()
                        Divider()
                        HStack {
                            Spacer()
                            Button { withAnimation(.easeInOut) {
                                let removed = days.remove(at: selectedIndex)
                                let day = Day(date: removed.date, id: removed.id, notes: notes, user: User(fullname: profileViewModel.fullname, email: profileViewModel.email, phone: profileViewModel.phone, userId: session.currentUser?.id ?? "", createdOn: Date().description))
                                servicesViewModel.updateVolunteerServiceDay(request: DayRequest(date: day.date, id: day.id, notes: day.notes, user: day.user)) {
                                        days.insert(day, at: selectedIndex)
                                        showVoluteerSheet.toggle()
                                    }
                                
                                UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                            }} label: {
                                Text("Volunteer")
                                    .foregroundColor(.white)
                                    .bold()
                                    .padding()
                                    .frame(maxWidth: .infinity)
                                    .background(Color(hex: Constants.MOMENTUM_ORANGE))
                                    .cornerRadius(10)
                                    .padding()
                            }
                            Spacer()
                            
                        }
                    }
                }
                .frame(maxWidth: screenBounds.width - 65, maxHeight: 560, alignment: .center)
                Spacer()
            }
            .onAppear {
                if !(session.currentUser?.isGuest ?? true) {
                    profileViewModel.getContactInformation(userId: session.currentUser?.id ?? "") {
                        profileViewModel.getBillingInformation(userId: session.currentUser?.id ?? "")
                    }
                }
            }
            .onTapGesture {
                UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
            }
        }

        .onAppear {
            days = service.days.sorted(by: { getDay(day: $0) < getDay(day: $1) })
        }
    }
    func getDate(day: Day) -> String {
        _ = "2016-04-14T10:44:00+0000"
        
        let dateFormatter = DateFormatter()
        dateFormatter.locale = Locale(identifier: "en_US_POSIX") // set locale to reliable US_POSIX
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ssZ"
        let date = dateFormatter.date(from: day.date) ?? Date()
        
        dateFormatter.dateFormat = "MMM d, EEE"
        return dateFormatter.string(from: date)
    }
    
    func getDay(day: Day) -> Int {
        return Int(getDate(day: day).split(separator: ",").first?.split(separator: " ").last ?? "0") ?? 0
    }
}

