//
//  MealDetailView.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/8/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI

struct MealDetailView: View {
    @State private var showRepcipentInfoSheet = false
    @State private var showVoluteerSheet = false
    @State private var meal = "\u{2022} "
    @State private var notes = "\u{2022} "
    @State private var selectedIndex = 0
    @State private var meals = [Meal]()

    var mealRequest: MealRequest
    var body: some View {
        
        ScrollView {
            VStack(alignment: .leading) {
                
                Card {
                    
                    VStack(alignment: .leading) {
                        Text("Repcipent")
                            .font(.title2)
                            .bold()
                        RecipentInfo(
                            title: "Allergies or Restrictions",
                            description: "None",
                            icon: "exclamationmark.shield"
                        )
                        RecipentInfo(
                            title: "Food for",
                            description: "\(mealRequest.numOfAdults) Adults, \(mealRequest.numOfKids) Kids",
                            icon: "person.3"
                        )
                        RecipentInfo(
                            title: "Drop-time",
                            description: mealRequest.preferredTime,
                            icon: "deskclock"
                        )
                        
                        Button {
                            showRepcipentInfoSheet.toggle()
                        } label: {
                            
                            VStack {
                                Text("View All Repcipent Info")
                                    .fontWeight(.medium)
                                    .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                                    .padding(.horizontal, 15)
                                    .padding(.vertical, 10)
                                    .frame(maxWidth: .infinity)
                                
                            } .overlay(
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(Color(hex: Constants.MOMENTUM_ORANGE), lineWidth: 2)
                            )
                        }
                    }.padding(10)
                    
                }
                Text("Meal Calendar")
                    .font(.title2)
                    .bold()
                
                ForEach(Array(meals.enumerated()), id: \.offset) { i, m in
                    Card {
                        HStack {
                            VStack(alignment: .leading) {
                                Text("Aug 8")
                                    .bold()
                                Text("Wed")
                            }.padding(10)
                            Divider()
                                .padding(.vertical, 5)
                            if !m.meal.isEmpty {
                                ZStack(alignment: .center) {
                                    
                                    Circle()
                                        .fill(Color(hex: Constants.MOMENTUM_ORANGE))
                                        .frame(width: 50, height: 50)
                                    
                                    
                                    Text("Mwai Banda"
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
                                if m.meal.isEmpty {
                                    Text("Available")
                                        .bold()
                                    Button {
                                        selectedIndex = i
                                        showVoluteerSheet.toggle()
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
                                    Text("Mwai Banda")
                                        .bold()
                                    Text(m.meal.trimmingCharacters(in: .whitespacesAndNewlines))
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
        .sheet(isPresented: $showRepcipentInfoSheet) {
            VStack(alignment: .leading) {
                Text("Repcipent Information")
                    .font(.title2)
                    .bold()
                    .padding()
                Divider()
                    .padding(.bottom)
                
                HStack {
                    RecipentInfo(
                        title: "Allergies or Restrictions",
                        description: "None",
                        icon: "exclamationmark.shield"
                    )
                    .padding()
                    Spacer()
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Color(hex: 0xFFFFEFC1))
                .clipShape(RoundedRectangle(cornerRadius: 10))
                .padding(.horizontal)
                
                
                RecipentInfo(
                    title: "Meal Drop-Off Location",
                    description:"""
                        \(mealRequest.street)
                        \(mealRequest.city)
                        \(mealRequest.phone)
                        \(mealRequest.instructions)
                        """.trimmingCharacters(in: .whitespacesAndNewlines),
                    icon: "location"
                )
                .padding()
                .padding(.horizontal)
                
                
                RecipentInfo(
                    title: "Preferred Drop-time Time",
                    description: mealRequest.preferredTime,
                    icon: "deskclock"
                )
                .padding()
                .padding(.horizontal)
                
                RecipentInfo(
                    title: "People to Cook for",
                    description: "\(mealRequest.numOfAdults) Adults, \(mealRequest.numOfKids) Kids",
                    icon: "person.3"
                )
                .padding()
                .padding(.horizontal)
                
                RecipentInfo(
                    title: "Favourite Meals or Restaurants",
                    description: "\(mealRequest.favourites)",
                    icon: "hand.thumbsup"
                )
                .padding()
                .padding(.horizontal)
                
                
                
                Spacer()
            }
            
        }
        .onAppear {
            if meals.isEmpty {
                meals = mealRequest.meals
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
                                Text("Aug 8")
                            }.padding(.leading, 15)
                            Divider()
                        }
                        
                        VStack(alignment: .leading) {
                            Text("Meal")
                                .foregroundColor(.gray)
                                .font(.headline)
                                .fontWeight(.semibold)
                            TextEditor(text: $meal)
                                .frame(minHeight: 120, maxHeight: 120)
                                .foregroundColor(.black)
                                .onChange(of: meal) { [meal] newText in
                                    if newText.suffix(1) == "\n" && newText > meal {
                                        self.meal.append("\u{2022} ")
                                    }
                                }
                        }.padding(.horizontal, 15)
                        Divider()
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
                                let removed = meals.remove(at: selectedIndex)
                                meals.insert(Meal(date: removed.date, meal: meal.replacingOccurrences(of: "\u{2022}", with: ""), notes: notes), at: selectedIndex)
                                showVoluteerSheet.toggle()
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
            .onTapGesture {
                UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
            }
        }
    }
}

struct RecipentInfo: View {
    var title: String
    var description: String
    var icon: String
    
    var body: some View {
        HStack(alignment: .firstTextBaseline) {
            Image(systemName: icon)
                .imageScale(.large)
                .frame(width: 40)
            VStack(alignment: .leading) {
                Text(title)
                    .font(.headline)
                    .bold()
                Text(description)
                    .font(.subheadline)
                
            }
        }.padding(.top, 10)
    }
}
