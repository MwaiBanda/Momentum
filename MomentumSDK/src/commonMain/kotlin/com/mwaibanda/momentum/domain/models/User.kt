package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val fullname: String,
    val email: String,
    val phone: String,
    @SerialName("id")
    val userId: String,
    val createdOn: String? = null
)
