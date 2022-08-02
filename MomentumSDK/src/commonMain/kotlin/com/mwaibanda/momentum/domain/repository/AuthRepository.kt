package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.UserResponse
import dev.gitlive.firebase.auth.ActionCodeResult
import dev.gitlive.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun signInWithEmail(email: String, password: String): UserResponse
    suspend fun signUpWithEmail(email: String, password: String): UserResponse
    suspend fun signInAsGuest(): UserResponse
    suspend fun getCurrentUser(): UserResponse
    suspend fun isUserSignedIn(): Boolean
    suspend fun deleteUser()
    suspend fun signOut()
}