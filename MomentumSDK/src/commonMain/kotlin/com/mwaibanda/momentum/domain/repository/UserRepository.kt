package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.User

interface UserRepository {
    suspend fun postUser(user: User)
    suspend fun getUserPhone(userID: String, phone: String)
    suspend fun getUser(userID: String)

}