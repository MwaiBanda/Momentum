package com.mwaibanda.momentum.data.mealDTO

import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.User
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MealDTO(
    val allergies: String,
    val city: String,
    val email: String,
    val favourites: String,
    val id: String,
    val instructions: String,
    val least_favourites: String,
    val meals: List<VolunteeredMealDTO>,
    val num_of_adults: Int,
    val number_of_kids: Int,
    @SerialName("participants")
    val participantDTOS: List<ParticipantDTO>?,
    val phone: String,
    val preferred_time: String,
    val reason: String,
    val recipient: String,
    val street: String,
    val user: User
) {
    fun toMeal(): Meal {
        return Meal(
            id = id,
            allergies = allergies,
            city = city,
            email = email,
            favourites = favourites,
            instructions = instructions,
            leastFavourites = least_favourites,
            meals = meals.map { it.toVolunteeredMeal() },
            numOfAdults = num_of_adults,
            numberOfKids = number_of_kids,
            participants = participantDTOS?.map { it.toParticipant() } ?: emptyList(),
            phone = phone,
            preferredTime = preferred_time,
            reason = reason,
            recipient = recipient,
            street = street,
            user = user
        )
    }
}