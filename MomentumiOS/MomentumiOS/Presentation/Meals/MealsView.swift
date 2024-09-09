//
//  Meals.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/6/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MultiDatePicker
import MomentumSDK
import Lottie


struct MealsView: View {
    @StateObject private var profileViewModel = ProfileViewModel()
    @State private var showAddMealSheet = false
    @State private var showAuthSheet = false
    @State private var meals = [Meal]()
    @StateObject private var mealViewModel = MealViewModel()
    @EnvironmentObject var session: Session

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Divider()
            HStack {
            Text(MultiplatformConstants.shared.MEALS_SUBHEADING.uppercased())
                .font(.caption2)
                .foregroundColor(Color(hex: Constants.MOMENTUM_ORANGE))
                .padding(.leading)
                .padding(.leading, 5)
                
                Spacer()
            }
            .padding(.top, 5)
            if #available(iOS 15, *) {
                MealsList(meals: $meals, mealViewModel: mealViewModel, profileViewModel: profileViewModel)
                    .refreshable {
                        meals = []
                        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5, execute: {
                            mealViewModel.getMeals(isResfreshing: true) { meals in
                                self.meals = meals
                            }
                        })
                    }
            } else {
                MealsList(meals: $meals, mealViewModel: mealViewModel, profileViewModel: profileViewModel)
            }
            Divider()
                .padding(.bottom, 5)
        }
        .onAppear {
            DispatchQueue.global().async {
                if !(session.currentUser?.isGuest ?? true) {
                    profileViewModel.getContactInformation(userId: session.currentUser?.id ?? "") {
                        profileViewModel.getBillingInformation(userId: session.currentUser?.id ?? "")
                    }
                }
                mealViewModel.getMeals { meal in
                    DispatchQueue.main.async {
                        meals = meal
                    }
                }
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .toolbar(
            content: {
                ToolbarItemGroup(placement: .navigationBarLeading) {
                    Text("Meals")
                        .font(.title3)
                        .fontWeight(.heavy)
                }
                
                ToolbarItemGroup(placement: .navigationBarTrailing) {
                    Button {
                        withAnimation(.easeInOut(duration: 0.35)) {
                            if (session.currentUser?.isGuest ?? true) {
                                showAuthSheet.toggle()
                            } else {
                                showAddMealSheet.toggle()
                            }
                        }
                    } label: {
                        Image(systemName: "plus")
                    }
                    
                }
            }
        )
        .sheet(isPresented: $showAuthSheet) {
            MomentumBlurredBackground {
                AuthControllerView()
            }
        }
        .sheet(isPresented: $showAddMealSheet) {
            ZStack {
                UploadMealCardView(onDismiss: { request in
                    mealViewModel.postMeal(request: request) { _ in
                        mealViewModel.getMeals { meals in
                            self.meals = meals
                        }
                    }
                    showAddMealSheet.toggle()
                })
            }.onTapGesture {
                UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
            }
        }
    }
}

struct Meals_Previews: PreviewProvider {
    static var previews: some View {
        MealsView()
    }
}

struct MealsList: View {
    @Binding var meals: [Meal]
    @ObservedObject var mealViewModel: MealViewModel
    @ObservedObject var profileViewModel: ProfileViewModel

    var body: some View {
        ScrollView(showsIndicators: false) {
            if meals.isEmpty {
                ForEach(0..<12, id: \.self) { _ in
                    DescriptionCard(title: "placeholder", description: "placeholder")
                }
            } else {
                ForEach(meals) { meal in
                    NavigationLink  {
                        MealDetailView(mealViewModel: mealViewModel, profileViewModel: profileViewModel, mealRequest: meal)
                    } label: {
                        DescriptionCard(title: meal.recipient, description: meal.reason)
                    }
                }
            }
        }
        .redacted(reason: meals.isEmpty ? .placeholder : [])
        .padding(.top, 15)
        .padding(.bottom, 10)
    }
}
struct UploadMealCardView: View {
    @State private var currentPage = Int()
    @State private var timer = Timer.publish(every: 3, on: .main, in: .common).autoconnect()
    @State private var loadingState: Double = 0
    @State private var startLoading = false
    @State private var loadingIncrement: Double = 1
    @EnvironmentObject var session: Session

    /* STATE PAGE 1*/
    @State private var recipient = ""
    @State private var email = ""
    @State private var phone = ""
    @State private var reason = ""
    
    /* STATE PAGE 2*/
    @State private var street = ""
    @State private var city = ""
    @State private var numberOfAdults = ""
    @State private var numberOfKids = ""
    @State private var preferredTime = ""
    @State private var favourites = "\u{2022} "
    /* STATE PAGE 2*/
    @State private var leastFavourites = "\u{2022} "
    @State private var allergies = "\u{2022} "
    @State private var instructions = "\u{2022} "
    
    
    @State var selectedDates = [Date]()
    
    var onDismiss: (MealRequest) -> Void
    
    var body: some View {
        VStack {
            Spacer()
            ZStack {
                RoundedRectangle(cornerRadius: 10, style: .continuous)
                    .fill(Color.white)
                    .shadow(color: Color.black.opacity(0.15), radius: 3)
                
                VStack {
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
                        HStack {
                            Text("\(currentPage + 1)").font(.caption) +
                            Text("/").font(.headline).fontWeight(.light) +
                            Text("5").font(.caption)
                        }.padding()
                            .foregroundColor(.gray)
                        
                    }.padding(.vertical)
                        .padding(.leading)
                        .padding(.top)
                    
                    if currentPage == 3 && startLoading {
                        ProgressView(value: loadingState, total: 101)
                            .progressViewStyle(LinearProgressViewStyle(tint: Color(hex: Constants.MOMENTUM_ORANGE)))
                        
                    } else {
                        Divider()
                    }
                    
                    TabView (selection: $currentPage) {
                        VStack {
                            Group {
                                DefaultTextfield(title: "Recipient", icon: "person", text: $recipient)
                                Divider()
                                DefaultTextfield(title: "Email", icon: "envelope", text: $email)
                                Divider()
                                DefaultTextfield(title: "Phone", icon: "phone", text: $phone)
                                Divider()
                            }
                            Group {
                                DefaultTextfield(title: "Reason", icon: "info.circle", text: $reason)
                                
                            }
                            Spacer()
                        }
                        .tag(0)
                        VStack {
                            MultiDatePicker(anyDays: $selectedDates)
                                .accentColor(.black)
                            Spacer()
                        }
                        .tag(1)
                        ScrollView(showsIndicators: false) {
                            VStack {
                                DefaultTextfield(title: "Street", icon: "building.2", text: $street)
                                Divider()
                                DefaultTextfield(title: "City", icon: "building.columns", text: $city)
                                Divider()
                                DefaultTextfield(title: "Number of Adults", icon: "person", text: $numberOfAdults)
                                Divider()
                                DefaultTextfield(title: "Number of Kids", icon: "person", text: $numberOfKids)
                                Divider()
                                DefaultTextfield(title: "Preferred Delivery Time(4pm - 5pm)", icon: "calendar.badge.clock", text: $preferredTime)
                                
                                Spacer()
                            }
                        }
                        .tag(2)
                        ScrollView(showsIndicators: false)  {
                            VStack(alignment: .leading) {
                                Text("Favorite Meals or Resturants")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                TextEditor(text: $favourites)
                                    .frame(minHeight: 120, maxHeight: 120)
                                    .foregroundColor(.black)
                                    .onChange(of: favourites) { [favourites] newText in
                                        if newText.suffix(1) == "\n" && newText > favourites {
                                            self.favourites.append("\u{2022} ")
                                        }
                                    }
                            }.padding(.horizontal, 15)
                            Divider()
                            VStack(alignment: .leading) {
                                Text("Least Favorite Meals")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                TextEditor(text: $leastFavourites)
                                    .frame(minHeight: 120, maxHeight: 120)
                                    .foregroundColor(.black)
                                    .onChange(of: leastFavourites) { [leastFavourites] newText in
                                        if newText.suffix(1) == "\n" && newText > leastFavourites {
                                            self.leastFavourites.append("\u{2022} ")
                                        }
                                    }
                            }.padding(.horizontal, 15)
                            Divider()
                            VStack(alignment: .leading) {
                                Text("Allergies or Dietary Restrictions")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                TextEditor(text: $allergies)
                                    .frame(minHeight: 120, maxHeight: 120)
                                    .foregroundColor(.black)
                                    .onChange(of: allergies) { [allergies] newText in
                                        if newText.suffix(1) == "\n" && newText > allergies {
                                            self.allergies.append("\u{2022} ")
                                        }
                                    }
                            }.padding(.horizontal, 15)
                            Divider()
                            VStack(alignment: .leading) {
                                Text("Additional Special Instructions")
                                    .foregroundColor(.gray)
                                    .font(.headline)
                                    .fontWeight(.semibold)
                                TextEditor(text: $instructions)
                                    .frame(minHeight: 120, maxHeight: 120)
                                    .foregroundColor(.black)
                                    .onChange(of: instructions) { [instructions] newText in
                                        if newText.suffix(1) == "\n" && newText > instructions {
                                            self.instructions.append("\u{2022} ")
                                        }
                                    }
                            }.padding(.horizontal, 15)
                        }
                        .tag(3)
                        VStack {
                            LottieView(animation: .named("postSuccess")).looping()
                                .frame(width: 200, height: 200, alignment: .center)
                            
                            Text("""
                               Your Meal has been posted successfully.
                               For more details. Check the Meals
                               section to see or edit previous meals,
                               in the Meals tab.
                               """)
                            .lineLimit(nil)
                            .fixedSize(horizontal: false, vertical: true)
                            .frame(maxHeight: .infinity)
                            .multilineTextAlignment(.center)
                            .padding()
                            .padding(.bottom)
                            Spacer()
                        }
                        .tag(4)
                    }.tabViewStyle(.page(indexDisplayMode: .never))
                    
                    Divider()
                    HStack {
                        Spacer()
                        Button { withAnimation(.easeInOut) {
                            if currentPage < 4 {
                                if currentPage == 3  {
                                    currentPage = 3
                                    startLoading = true
                                    startTimer()
                                } else {
                                    currentPage += 1
                                }
                            } else {
                                onDismiss(MealRequest(allergies: allergies, city: city, email: email, favourites: favourites, instructions: instructions, leastFavourites: leastFavourites, meals: selectedDates.map({  MealVolunteerRequest.init(description: "", notes: "", mealId: "", userId: "", date: $0.description) }), numOfAdults: Int32(numberOfAdults) ?? 0, numberOfKids: Int32(numberOfKids) ?? 0, phone: phone, preferredTime: preferredTime, reason: reason, recipient: recipient, street: street, userId: session.currentUser?.id ?? ""))
                            }
                            
                        }} label: {
                            Text(currentPage < 3 ? "Next" : currentPage < 4 ? "Save" : "Dismiss")
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
            .onReceive(timer) { _ in
                if startLoading && loadingState < 101 {
                    loadingState += loadingIncrement
                } else if loadingState > 99 {
                    currentPage += 1
                    stopTimer()
                    
                }
            }
            
            Spacer()
        }
    }
    func stopTimer() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
        self.timer.upstream.connect().cancel()
    }
    
    func startTimer() {
        self.timer = Timer.publish(every: 0.01, on: .main, in: .common).autoconnect()
    }
}


struct DefaultTextfield: View {
    var title: String
    var icon: String
    @Binding var text: String
    var body: some View {
        TextField(title, text: $text)
            .textContentType(.username)
            .disableAutocorrection(true)
            .keyboardType(.emailAddress)
            .autocapitalization(.none)
            .padding(.leading, 24)
            .padding(15)
            .foregroundColor(Color.black)
            .cornerRadius(7)
            .disableAutocorrection(false)
            .overlay(
                HStack{
                    Image(systemName: icon)
                    Spacer()
                }
                    .padding(.horizontal, 12)
                    .foregroundColor(Color.gray)
                
            )
            .ignoresSafeArea(.keyboard, edges: .bottom)
    }
}

struct DescriptionCard: View {
    var title: String
    var description: String
    
    var body: some View {
        Card {
            HStack {
                VStack(alignment: .leading) {
                    Text(title)
                        .font(.title3)
                        .fontWeight(.bold)
                        .foregroundColor(Color.black)
                    Text(description)
                        .foregroundColor(.gray)
                }
                Spacer()
                Image(systemName: "chevron.right")
                    .foregroundColor(Color.black)
            }
            .padding(5)
            .padding(.horizontal, 10)
            .padding(.vertical, 10)
        }
        .frame(maxHeight: 70)
        .padding(.horizontal, 10)
        .padding(.top, 5)
    }
}
