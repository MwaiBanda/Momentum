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
import com.mwaibanda.momentum.utils.DataResponse
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
    override suspend fun fetchAllMeals(): DataResponse<MealResponse> {
        val cacheMeals = getItemUseCase(MEALS_KEY).orEmpty()
        if (cacheMeals.isNotEmpty()) {
            return DataResponse.Success(cacheMeals)
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
            return  DataResponse.Failure(e.message.toString())
        }
        val newlyCachedMeals = getItemUseCase(MEALS_KEY).orEmpty()
        return DataResponse.Success(newlyCachedMeals)
    }

    override suspend fun postMeal(request: MealRequest): DataResponse<Meal> {
        return try {
            val meal: Meal = httpClient.post {
                momentumAPI(MEALS_ENDPOINT, request)
            }.body<MealRequest>().toMeal()
            DataResponse.Success(meal)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }

    override suspend fun postVolunteeredMeal(request: VolunteeredMealRequest): DataResponse<VolunteeredMeal> {
        return try {
            val meal: VolunteeredMeal = httpClient.post {
                momentumAPI(VOLUNTEERED_MEAL_ENDPOINT, request)
            }.body<VolunteeredMealRequest>().volunteeredMeal
            DataResponse.Success(meal)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }

    companion object {
        const val MEALS_KEY = "meals"
    }
}