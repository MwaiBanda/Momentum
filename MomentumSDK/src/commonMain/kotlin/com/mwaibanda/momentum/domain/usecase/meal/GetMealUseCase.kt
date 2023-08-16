package com.mwaibanda.momentum.domain.usecase.meal

import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.domain.repository.MealRepository
import com.mwaibanda.momentum.utils.Result

class GetMealUseCase(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(onCompletion: (Result<List<Meal>>) -> Unit) {
        onCompletion(mealRepository.getAllMeals())
    }
}