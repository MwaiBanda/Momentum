package com.mwaibanda.momentum.domain.controller

import dev.gitlive.firebase.auth.AuthResult

interface AuthController {
    fun signInWithEmail(email: String, password: String, onCompletion: (AuthResult) -> Unit)
    fun signUpWithEmail(email: String, password: String, onCompletion: (AuthResult) -> Unit)
    fun signInAsGuest(onCompletion: (AuthResult) -> Unit)
    fun checkAuthAndSignAsGuest(onCompletion: (AuthResult) -> Unit)
}