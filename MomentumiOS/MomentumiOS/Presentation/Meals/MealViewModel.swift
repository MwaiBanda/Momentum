//
//  MealViewModel.swift
//  MomentumiOS
//
//  Created by Mwai Banda on 8/14/23.
//  Copyright © 2023 Momentum. All rights reserved.
//

import Foundation
import TinyDi
import MomentumSDK

class MealViewModel: ObservableObject {
    @Inject private var controller: MealController
    
    func getMeals(onCompletion: @escaping ([Meal]) -> Void) {
        controller.getAllMeals { res in
            if let meals = res.data as? [Meal] {
                onCompletion(meals)
            }
        }
    }
    
    func postMeal(request: MealRequest, onCompletion: @escaping (Meal) -> Void) {
        controller.postMeal(request: request) { res in
            if let meal = res.data {
                onCompletion(meal)
            }
        }
    }
    func postVolunteeredMeal(request: VolunteeredMealRequest, onCompletion: @escaping (VolunteeredMeal) -> Void) {
        controller.postVolunteeredMeal(request: request) { res in
            if let meal = res.data {
                onCompletion(meal)
            }
        }
    }

}
