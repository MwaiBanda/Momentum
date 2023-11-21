package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.mealDTO.MealContainerDTO
import com.mwaibanda.momentum.data.mealDTO.MealRequest
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealRequest
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import com.mwaibanda.momentum.domain.repository.MealRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MealRepositoryImpl(
    private val httpClient: HttpClient
): MomentumBase(), MealRepository {
    override suspend fun fetchAllMeals(): Result<List<Meal>> {
        return try {
            val meals: MealContainerDTO = httpClient.get {
                momentumAPI(MEALS_ENDPOINT)
            }.body()
            Result.Success(meals.map { it.toMeal() })
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun postMeal(request: MealRequest): Result<Meal> {
        return try {
            val meal: Meal = httpClient.post {
                momentumAPI(MEALS_ENDPOINT)
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<MealRequest>().toMeal()
            Result.Success(meal)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun postVolunteeredMeal(request: VolunteeredMealRequest): Result<VolunteeredMeal> {
        return try {
            val meal: VolunteeredMeal = httpClient.post {
                momentumAPI(VOLUNTEERED_MEAL_ENDPOINT)
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<VolunteeredMealRequest>().volunteeredMeal
            Result.Success(meal)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }
}