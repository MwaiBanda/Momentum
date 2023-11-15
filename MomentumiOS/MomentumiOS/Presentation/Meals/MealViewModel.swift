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
    @Inject private var mealController: MealController
    @Inject private var notificationController: NotificationController

    func getMeals(onCompletion: @escaping ([Meal]) -> Void) {
        mealController.getAllMeals { res in
            if let meals = res.data as? [Meal] {
                onCompletion(meals)
            }
        }
    }
    
    func postMeal(request: MealRequest, onCompletion: @escaping (Meal) -> Void) {
        mealController.postMeal(request: request) { [weak self] res in
            if let meal = res.data {
                onCompletion(meal)
                self?.notificationController.sendNotification(notification: Notification(
                    title: "Momentum Church: Indiana",
                    body: "We have a new meal, organised by \(meal.user.fullname) for the \(meal.recipient)",
                    topic: MultiplatformConstants.shared.ALL_USERS_TOPIC
                )) { res in
                    if let data = res.data {
                        print("Notification posted successfully")
                    }
                }
            }
        }
    }
    func postVolunteeredMeal(request: VolunteeredMealRequest, onCompletion: @escaping (VolunteeredMeal) -> Void) {
        mealController.postVolunteeredMeal(request: request) { res in
            if let meal = res.data {
                onCompletion(meal)
            }
        }
    }

}
