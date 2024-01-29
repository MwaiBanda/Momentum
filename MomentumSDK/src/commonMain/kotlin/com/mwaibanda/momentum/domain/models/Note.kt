package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: String,
    val content: String,
) {
    @Serializable
    data class UserNote(
        val id: String,
        val content: String,
        val userId: String?
    )
    fun toUserNote(userId: String) = UserNote(
        id = id,
        content = content,
        userId = userId,
    )
}
