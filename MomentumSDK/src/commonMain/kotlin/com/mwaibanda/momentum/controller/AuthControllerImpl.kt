package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.domain.usecase.SignInAsGuestUseCase
import com.mwaibanda.momentum.domain.usecase.SignInWithEmailUseCase
import com.mwaibanda.momentum.domain.usecase.SignUpWithEmailUseCase
import dev.gitlive.firebase.auth.AuthResult
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AuthControllerImpl(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase
): AuthController {
    private val scope = MainScope()

    override fun signInWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult) -> Unit
    ) {
        scope.launch {
           signInWithEmailUseCase(email = email, password = password) {
               onCompletion(it)
           }
        }
    }

    override fun signUpWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult) -> Unit
    ) {
        scope.launch {
            signUpWithEmailUseCase(email = email, password = password) {
                onCompletion(it)
            }
        }
    }

    override fun signInAsGuest(onCompletion: (AuthResult) -> Unit) {
        scope.launch {
            signInAsGuestUseCase {
                onCompletion(it)
            }
        }
    }
}