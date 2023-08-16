package com.mwaibanda.momentum.data.mealDTO

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MealVolunteerRequest(
    val description: String,
    val notes: String,
    @SerialName("meal_id")
    val mealId: String,
    @SerialName("user_id")
    val userId: String,
    val date: String
)