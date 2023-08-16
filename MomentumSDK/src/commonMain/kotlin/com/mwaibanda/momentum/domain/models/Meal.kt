package com.mwaibanda.momentum.domain.models

import com.mwaibanda.momentum.data.mealDTO.ParticipantDTO
import com.mwaibanda.momentum.data.mealDTO.VolunteeredMealDTO

data class Meal(
    val id: String,
    val allergies: String,
    val city: String,
    val email: String,
    val favourites: String,
    val instructions: String,
    val leastFavourites: String,
    val meals: List<VolunteeredMeal>,
    val numOfAdults: Int,
    val numberOfKids: Int,
    val participants: List<Participant>,
    val phone: String,
    val preferredTime: String,
    val reason: String,
    val recipient: String,
    val street: String,
    val user: User
)
