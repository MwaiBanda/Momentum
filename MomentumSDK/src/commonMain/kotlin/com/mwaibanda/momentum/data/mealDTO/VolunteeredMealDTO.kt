package com.mwaibanda.momentum.data.mealDTO

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.models.VolunteeredMeal

@kotlinx.serialization.Serializable
data class VolunteeredMealDTO(
    val created_on: String,
    val description: String,
    val id: String,
    val notes: String,
    val user: User
) {
    fun toVolunteeredMeal(): VolunteeredMeal {
        return VolunteeredMeal(
            id = id,
            createdOn = created_on,
            description = description,
            notes = notes,
            user = user
        )
    }
}