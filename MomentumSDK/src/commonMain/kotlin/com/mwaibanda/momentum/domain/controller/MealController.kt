package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.utils.Result

interface MealController {
    fun getAllMeals(onCompletion: (Result<List<Meal>>) -> Unit)
    fun postMeal(request: MealRequest, onCompletion: (Result<Meal>) -> Unit)
    fun postVolunteeredMeal(request: VolunteeredMealRequest, onCompletion: (Result<VolunteeredMeal>) -> Unit)
}