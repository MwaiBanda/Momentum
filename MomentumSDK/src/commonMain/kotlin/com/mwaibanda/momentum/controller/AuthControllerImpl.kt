package com.mwaibanda.momentum.controller


import com.mwaibanda.momentum.domain.controller.AuthController
import io.github.mwaibanda.authentication.domain.controller.AuthenticationController
import io.github.mwaibanda.authentication.domain.model.UserResponse
import io.github.mwaibanda.authentication.utils.AuthResult
import io.github.mwaibanda.authentication.utils.DefaultAuthResult

class AuthControllerImpl(
    private val controller: AuthenticationController
): AuthController {
    override fun resetPassword(email: String, onCompletion: (DefaultAuthResult) -> Unit) {
        controller.resetPassword(email, onCompletion)
    }

    override fun signInWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult<UserResponse>) -> Unit
    ) {
        controller.signInWithEmail(email, password, onCompletion)
    }

    override fun signUpWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult<UserResponse>) -> Unit
    ) {
        controller.signUpWithEmail(email, password, onCompletion)
    }

    override fun signInAsGuest(onCompletion: (AuthResult<UserResponse>) -> Unit) {
        controller.signInAsGuest(onCompletion)
    }

    override fun checkAuthAndSignAsGuest(onCompletion: (AuthResult<UserResponse>) -> Unit) {
        controller.checkAuthAndSignAsGuest(onCompletion)
    }

    override fun getCurrentUser(onCompletion: (AuthResult<UserResponse>) -> Unit) {
        controller.getCurrentUser(onCompletion)
    }

    override fun isUserSignedIn(onCompletion: (Boolean) -> Unit) {
        controller.isUserSignedIn(onCompletion)
    }

    override fun deleteUser() {
        controller.deleteUser()
    }

    override fun signOut() {
        controller.signOut()
    }
}