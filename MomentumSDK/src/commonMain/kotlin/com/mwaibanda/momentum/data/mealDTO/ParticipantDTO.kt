package com.mwaibanda.momentum.data.mealDTO

import com.mwaibanda.momentum.domain.models.Participant
import com.mwaibanda.momentum.domain.models.User

@kotlinx.serialization.Serializable
data class ParticipantDTO(
    val id: String,
    val user: User
) {
    fun toParticipant(): Participant {
        return Participant(id, user)
    }
}