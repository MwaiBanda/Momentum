package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.UserResponse
import dev.gitlive.firebase.auth.AuthResult

interface AuthController {
    fun signInWithEmail(email: String, password: String, onCompletion: (UserResponse) -> Unit)
    fun signUpWithEmail(email: String, password: String, onCompletion: (UserResponse) -> Unit)
    fun signInAsGuest(onCompletion: (UserResponse) -> Unit)
    fun checkAuthAndSignAsGuest(onCompletion: (UserResponse) -> Unit)
    fun getCurrentUser(onCompletion: (UserResponse) -> Unit)
    fun isUserSignedIn(onCompletion: (Boolean) -> Unit)
    fun deleteUser()
    fun signOut()
}