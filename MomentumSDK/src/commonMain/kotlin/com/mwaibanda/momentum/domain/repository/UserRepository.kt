package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.utils.Result

interface UserRepository {
    suspend fun postUser(user: User)
    suspend fun updateUser(user: User): User
    suspend fun fetchUser(userId: String): Result<User>
    suspend fun deleteUser(userId: String)
}