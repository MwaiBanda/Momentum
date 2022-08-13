package com.mwaibanda.momentum.android.presentation.auth

import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.utils.Result

class FakeAuthController: AuthController {
    override fun signInWithEmail(
        email: String,
        password: String,
        onCompletion: (Result<UserResponse>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun signUpWithEmail(
        email: String,
        password: String,
        onCompletion: (Result<UserResponse>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun signInAsGuest(onCompletion: (Result<UserResponse>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkAuthAndSignAsGuest(onCompletion: (Result<UserResponse>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(onCompletion: (Result<UserResponse>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun isUserSignedIn(onCompletion: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun deleteUser() {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}