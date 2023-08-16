package com.mwaibanda.momentum.data.mealDTO

import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import kotlinx.serialization.SerialName
@kotlinx.serialization.Serializable
data class VolunteeredMealRequest(
    @SerialName("meal_id")
    val  mealId: String,
    @SerialName("volunteered_meal")
    val volunteeredMeal: VolunteeredMeal
)
