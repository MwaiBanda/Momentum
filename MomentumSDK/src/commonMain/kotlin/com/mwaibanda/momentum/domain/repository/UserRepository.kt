package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.utils.DataResponse

interface UserRepository {
    suspend fun postUser(user: User)
    suspend fun updateUser(user: User): DataResponse<User>
    suspend fun fetchUser(userId: String): DataResponse<User>
    suspend fun deleteUser(userId: String)
}