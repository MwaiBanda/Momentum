package com.mwaibanda.momentum.data.mealDTO

import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.models.VolunteeredMeal
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MealRequest(
    val allergies: String,
    val city: String,
    val email: String,
    val favourites: String,
    val instructions: String,
    @SerialName("least_favourites")
    val leastFavourites: String,
    val meals: List<MealVolunteerRequest>,
    @SerialName("num_of_adults")
    val numOfAdults: Int,
    @SerialName("number_of_kids")
    val numberOfKids: Int,
    val phone: String,
    @SerialName("preferred_time")
    val preferredTime: String,
    val reason: String,
    val recipient: String,
    val street: String,
    @SerialName("user_id")
    val userId: String
){
    fun toMeal(): Meal {
        return Meal(
            id = "M${(100 downTo 10).toList().random()}-${(1000 downTo 100).toList().random()}-${(10 downTo 0).toList().random()}-${(10000 downTo 1000).toList().random()}",
            allergies = allergies,
            city = city,
            email = email,
            favourites = favourites,
            instructions = instructions,
            leastFavourites = leastFavourites,
            meals = meals.map { VolunteeredMeal(id = "${
                (100 downTo 10).toList().random()
            }-${(1000 downTo 100).toList().random()}-${
                (10 downTo 0).toList().random()
            }-${(10000 downTo 1000).toList().random()}","","", "", "", User("", "","", it.userId, "")) },
            numOfAdults = numOfAdults,
            numberOfKids = numberOfKids,
            participants = emptyList(),
            phone = phone,
            preferredTime = preferredTime,
            reason = reason,
            recipient = recipient,
            street = street,
            user =  User("","","", userId, "")
        )
    }
}