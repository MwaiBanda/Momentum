package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val uid: String,
    val email: String?,
    val isAnonymous: Boolean
)