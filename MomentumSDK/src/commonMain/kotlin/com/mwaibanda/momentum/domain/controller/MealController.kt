package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.utils.DataResponse

interface MealController {
    fun getAllMeals(onCompletion: (DataResponse<List<Meal>>) -> Unit)
    fun postMeal(request: MealRequest, onCompletion: (DataResponse<Meal>) -> Unit)
    fun postVolunteeredMeal(request: VolunteeredMealRequest, onCompletion: (DataResponse<VolunteeredMeal>) -> Unit)

    fun clearMealsCache()
}