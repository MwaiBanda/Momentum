package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.utils.Result

interface MealRepository {
    suspend fun fetchAllMeals(): Result<List<Meal>>
    suspend fun postMeal(request: MealRequest): Result<Meal>
    suspend fun postVolunteeredMeal(request: VolunteeredMealRequest): Result<VolunteeredMeal>
}