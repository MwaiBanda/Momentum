package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.utils.Result

interface AuthController {
    fun signInWithEmail(email: String, password: String, onCompletion: (Result<UserResponse>) -> Unit)
    fun signUpWithEmail(email: String, password: String, onCompletion: (Result<UserResponse>) -> Unit)
    fun signInAsGuest(onCompletion: (Result<UserResponse>) -> Unit)
    fun checkAuthAndSignAsGuest(onCompletion: (Result<UserResponse>) -> Unit)
    fun getCurrentUser(onCompletion: (Result<UserResponse>) -> Unit)
    fun isUserSignedIn(onCompletion: (Boolean) -> Unit)
    fun deleteUser()
    fun signOut()
}