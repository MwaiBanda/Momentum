package com.mwaibanda.momentum.domain.usecase.meal

import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.repository.MealRepository
import com.mwaibanda.momentum.utils.Result

class PostMealUseCase(
private val mealRepository: MealRepository
) {
    suspend operator fun invoke(request: MealRequest, onCompletion: (Result<Meal>) -> Unit) {
        onCompletion(mealRepository.postMeal(request))
    }
}