package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.UserRequest

interface UserRepository {
    suspend fun postUser(userRequest: UserRequest)
    suspend fun updateUserEmail(userId: String, email: String)
    suspend fun updateUserPhone(userId: String, phone: String)
    suspend fun updateUserFullname(userId: String, fullname: String)
    suspend fun deleteUser(userId: String)
}