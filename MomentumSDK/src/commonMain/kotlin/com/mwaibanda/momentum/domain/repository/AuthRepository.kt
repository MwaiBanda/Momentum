package com.mwaibanda.momentum.domain.repository

import dev.gitlive.firebase.auth.ActionCodeResult
import dev.gitlive.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun signInWithEmail(email: String, password: String): AuthResult
    suspend fun signUpWithEmail(email: String, password: String): AuthResult
    suspend fun signInAsGuest(): AuthResult
}