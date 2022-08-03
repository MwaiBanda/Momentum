package com.mwaibanda.momentum.android.presentation.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.AuthController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class AuthViewModel(
    private val authController: AuthController
): ViewModel() {
    var user: User? by mutableStateOf(null)

    init {
        checkAndSignIn()
    }

    private fun checkAndSignIn() {
        authController.checkAuthAndSignAsGuest { res ->
            user = User(
                id = res.uid,
                email = res.email,
                isGuest = res.isAnonymous
            )
            Log.d("Auth", "User {id: ${res.uid}, isGuest: ${res.isAnonymous}}")
        }
    }
    data class User(
        val id: String,
        val email: String?,
        val isGuest: Boolean
    )
}
