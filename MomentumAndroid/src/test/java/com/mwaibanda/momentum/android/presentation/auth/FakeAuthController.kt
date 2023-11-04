package com.mwaibanda.momentum.android.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mwaibanda.momentum.domain.controller.AuthController
import io.github.mwaibanda.authentication.domain.model.UserResponse
import io.github.mwaibanda.authentication.utils.AuthResult

class FakeAuthController: AuthController {
    private var currentUser: UserResponse? by mutableStateOf(null)


    override fun signInWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult<UserResponse>) -> Unit
    ) {
        currentUser = UserResponse(
            uid = "1001-01",
            email = email,
            isAnonymous = false
        )
        onCompletion(AuthResult.Success(currentUser!!))
    }

    override fun signUpWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult<UserResponse>) -> Unit
    ) {
        currentUser = UserResponse(
            uid = "1001-01",
            email = email,
            isAnonymous = false
        )
        onCompletion(AuthResult.Success(currentUser!!))
    }

    override fun signInAsGuest(onCompletion: (AuthResult<UserResponse>) -> Unit) {
        currentUser = UserResponse(
            uid = "1001-01",
            email = "guest@momentum.com",
            isAnonymous = true
        )
        onCompletion(AuthResult.Success(currentUser!!))
    }

    override fun checkAuthAndSignAsGuest(onCompletion: (AuthResult<UserResponse>) -> Unit) {
        if (currentUser != null) {
            onCompletion(AuthResult.Success(currentUser!!))
        } else {
            signInAsGuest {
                onCompletion(it)
            }
        }
    }

    override fun getCurrentUser(onCompletion: (AuthResult<UserResponse>) -> Unit) {
        if (currentUser != null) {
            onCompletion(AuthResult.Success(currentUser!!))
        } else {
            onCompletion(AuthResult.Failure("No user"))
        }
    }

    override fun isUserSignedIn(onCompletion: (Boolean) -> Unit) {
        if (currentUser != null)
            onCompletion(true)
        else
            onCompletion(false)
    }

    override fun deleteUser() {
        currentUser = null
    }

    override fun signOut() {
        currentUser = null
    }
}