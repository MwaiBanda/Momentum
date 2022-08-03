package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.User

interface UserRepository {
    suspend fun postUser(user: User)
    suspend fun getUser(userId: String): User
    suspend fun updateUserEmail(userId: String, email: String)
    suspend fun updateUserPhone(userId: String, phone: String)
    suspend fun updateUserFullname(userId: String, fullname: String)
    suspend fun deleteUser(userId: String)
}