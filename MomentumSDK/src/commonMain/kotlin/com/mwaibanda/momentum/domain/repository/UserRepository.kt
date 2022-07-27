package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.User

interface UserRepository {
    suspend fun postUser(user: User)
    suspend fun updateUserEmail(userID: String, email: String)
    suspend fun updateUserPhone(userID: String, phone: String)
    suspend fun updateUserFullname(userID: String, fullname: String)
    suspend fun deleteUser(userID: String)
}