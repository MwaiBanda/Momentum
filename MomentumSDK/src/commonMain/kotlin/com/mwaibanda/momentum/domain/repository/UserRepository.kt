package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.utils.Result

interface UserRepository {
    suspend fun postUser(user: User)
    suspend fun getUser(userId: String): Result<User>
    suspend fun updateUserEmail(userId: String, email: String)
    suspend fun updateUserPhone(userId: String, phone: String)
    suspend fun updateUserFullname(userId: String, fullname: String)
    suspend fun deleteUser(userId: String)
}