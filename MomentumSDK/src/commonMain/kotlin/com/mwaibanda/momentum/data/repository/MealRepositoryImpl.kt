package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MealResponse
import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.mealDTO.MealContainerDTO
import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.domain.repository.MealRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.delay

class MealRepositoryImpl(
    private val httpClient: HttpClient,
    private val getItemUseCase: GetItemUseCase<MealResponse>,
    private val setItemUseCase: SetItemUseCase<MealResponse>,
): MomentumBase(), MealRepository {
    override suspend fun fetchAllMeals(): Result<MealResponse> {
        val cacheMeals = getItemUseCase(MEALS_KEY).orEmpty()
        if (cacheMeals.isNotEmpty()) {
            return Result.Success(cacheMeals)
        }
        try {
            val mealDTO: MealContainerDTO = httpClient.get {
                momentumAPI(MEALS_ENDPOINT)
            }.body()
            val meals = mealDTO.map { it.toMeal() }
            if (meals.isNotEmpty()) {
                setItemUseCase(MEALS_KEY, meals)
            } else {
                delay(30000)
                fetchAllMeals()
            }
        } catch (e: Exception) {
            return  Result.Failure(e.message.toString())
        }
        val newlyCachedMeals = getItemUseCase(MEALS_KEY).orEmpty()
        return Result.Success(newlyCachedMeals)
    }

    override suspend fun postMeal(request: MealRequest): Result<Meal> {
        return try {
            val meal: Meal = httpClient.post {
                momentumAPI(MEALS_ENDPOINT, request)
            }.body<MealRequest>().toMeal()
            Result.Success(meal)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun postVolunteeredMeal(request: VolunteeredMealRequest): Result<VolunteeredMeal> {
        return try {
            val meal: VolunteeredMeal = httpClient.post {
                momentumAPI(VOLUNTEERED_MEAL_ENDPOINT, request)
            }.body<VolunteeredMealRequest>().volunteeredMeal
            Result.Success(meal)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    companion object {
        const val MEALS_KEY = "meals"
    }
}