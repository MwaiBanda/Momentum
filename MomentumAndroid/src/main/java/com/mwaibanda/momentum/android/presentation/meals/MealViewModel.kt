package com.mwaibanda.momentum.android.presentation.meals

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.controller.MealController
import com.mwaibanda.momentum.domain.controller.NotificationController
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.mwaibanda.momentum.utils.Result

class MealViewModel(
    private val mealController: MealController,
    private val notificationController: NotificationController
): ViewModel() {
    fun getMeals(onCompletion: (List<Meal>) -> Unit) {
        mealController.getAllMeals {
            when (it) {
                is Result.Failure -> {
                    Log.e("MealViewModel[getAllMeals]", it.message ?: "" )
                }
                is Result.Success -> {
                    onCompletion(it.data ?: emptyList())
                }
            }
        }
    }

    fun postMeal(request: MealRequest, onCompletion: (Meal) -> Unit) {
        mealController.postMeal(request) {
            when (it) {
                is Result.Failure -> {
                    Log.e("MealViewModel[postMeal]", it.message ?: "" )
                }
                is Result.Success -> {
                    it.data?.let { meal ->
                        onCompletion(meal)
                        notificationController.sendNotification(
                            notification = Notification(
                                title = "Momentum Church: Indiana",
                                body = "We have a new meal, organised by ${meal.user.fullname} for the ${meal.recipient}",
                                topic = MultiplatformConstants.ALL_USERS_TOPIC
                            )
                        ){}
                    }

                }
            }
        }
    }

    fun postVolunteeredMeal(request: VolunteeredMealRequest, onCompletion: (VolunteeredMeal) -> Unit) {
        mealController.postVolunteeredMeal(request) {
            when (it) {
                is Result.Failure -> {
                    Log.e("MealViewModel[postVolunteeredMeal]", it.message ?: "" )
                }
                is Result.Success -> {
                    it.data?.let(onCompletion)
                }
            }
        }
    }
}