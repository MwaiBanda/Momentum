package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.data.repository.MealRepositoryImpl
import com.mwaibanda.momentum.domain.controller.MealController
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.domain.usecase.cache.InvalidateItemsUseCase
import com.mwaibanda.momentum.domain.usecase.meal.GetMealUseCase
import com.mwaibanda.momentum.domain.usecase.meal.PostMealUseCase
import com.mwaibanda.momentum.domain.usecase.meal.PostVolunteeredMealUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MealControllerImpl: MealController, KoinComponent {
    private val getMealUseCase: GetMealUseCase by inject()
    private val postMealUseCase: PostMealUseCase by inject()
    private val postVolunteeredMealUseCase: PostVolunteeredMealUseCase by inject()
    private val invalidateItemsUseCase: InvalidateItemsUseCase by inject()
    private val scope: CoroutineScope = MainScope()
    override fun getAllMeals(onCompletion: (Result<List<Meal>>) -> Unit) {
        scope.launch {
            getMealUseCase(onCompletion)
        }
    }

    override fun postMeal(request: MealRequest, onCompletion: (Result<Meal>) -> Unit) {
        scope.launch {
            postMealUseCase(request, onCompletion)
        }
    }

    override fun postVolunteeredMeal(
        request: VolunteeredMealRequest,
        onCompletion: (Result<VolunteeredMeal>) -> Unit,
    ) {
        scope.launch {
            postVolunteeredMealUseCase(request, onCompletion)
        }
    }

    override fun clearMealsCache() {
        invalidateItemsUseCase(MealRepositoryImpl.MEALS_KEY)
    }
}