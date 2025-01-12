//
//  Meals.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/6/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import SwiftUI
import MomentumSDK


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
        .navigationTitle("Meals")
        .toolbar(
            content: {
                
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
                        MealDetailView(mealViewModel: mealViewModel, profileViewModel: profileViewModel, meal: meal)
                    } label: {
                        DescriptionCard(title: meal.recipient, description: meal.reason)
                            .padding(.bottom, 5)
                            .padding([.bottom, .horizontal], 5)
                    }
                }
            }
        }
        .redacted(reason: meals.isEmpty ? .placeholder : [])
        .padding(.top, 15)
        .padding(.bottom, 10)
    }
}






