package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val fullname: String,
    val email: String,
    val phone: String,
    val userId: String,
    val createdOn: String
)
