package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.utils.DataResponse

interface MealRepository {
    suspend fun fetchAllMeals(): DataResponse<List<Meal>>
    suspend fun postMeal(request: MealRequest): DataResponse<Meal>
    suspend fun postVolunteeredMeal(request: VolunteeredMealRequest): DataResponse<VolunteeredMeal>
}