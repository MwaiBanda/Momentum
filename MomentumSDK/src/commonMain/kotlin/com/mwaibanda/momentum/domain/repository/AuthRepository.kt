package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.utils.Result

interface AuthRepository {
    suspend fun signInWithEmail(email: String, password: String): Result<UserResponse>
    suspend fun signUpWithEmail(email: String, password: String): Result<UserResponse>
    suspend fun signInAsGuest(): Result<UserResponse>
    suspend fun getCurrentUser(): Result<UserResponse>
    suspend fun isUserSignedIn(): Boolean
    suspend fun deleteUser()
    suspend fun signOut()
}