package com.mwaibanda.momentum.domain.usecase.meal

import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.domain.repository.MealRepository
import com.mwaibanda.momentum.utils.Result

class PostVolunteeredMealUseCase(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(request: VolunteeredMealRequest, onCompletion: (Result<VolunteeredMeal>) -> Unit) {
        onCompletion(mealRepository.postVolunteeredMeal(request))
    }
}