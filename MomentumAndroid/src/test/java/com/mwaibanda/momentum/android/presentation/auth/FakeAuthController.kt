package com.mwaibanda.momentum.android.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.utils.Result

class FakeAuthController: AuthController {
    private var currentUser: UserResponse? by mutableStateOf(null)
    override fun signInWithEmail(
        email: String,
        password: String,
        onCompletion: (Result<UserResponse>) -> Unit
    ) {
        currentUser = UserResponse(
            uid = "1001-01",
            email = email,
            isAnonymous = false
        )
        onCompletion(Result.Success(currentUser!!))
    }

    override fun signUpWithEmail(
        email: String,
        password: String,
        onCompletion: (Result<UserResponse>) -> Unit
    ) {
        currentUser = UserResponse(
            uid = "1001-01",
            email = email,
            isAnonymous = false
        )
        onCompletion(Result.Success(currentUser!!))
    }

    override fun signInAsGuest(onCompletion: (Result<UserResponse>) -> Unit) {
        currentUser = UserResponse(
            uid = "1001-01",
            email = "guest@momentum.com",
            isAnonymous = true
        )
        onCompletion(Result.Success(currentUser!!))
    }

    override fun checkAuthAndSignAsGuest(onCompletion: (Result<UserResponse>) -> Unit) {
        if (currentUser != null) {
            onCompletion(Result.Success(currentUser!!))
        } else {
            signInAsGuest {
                onCompletion(it)
            }
        }
    }

    override fun getCurrentUser(onCompletion: (Result<UserResponse>) -> Unit) {
        if (currentUser != null) {
            onCompletion(Result.Success(currentUser!!))
        } else {
            onCompletion(Result.Failure("No user"))
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