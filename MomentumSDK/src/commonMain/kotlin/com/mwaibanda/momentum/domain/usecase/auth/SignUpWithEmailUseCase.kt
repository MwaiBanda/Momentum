package com.mwaibanda.momentum.domain.usecase.auth

import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.AuthResult

class SignUpWithEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, onCompletion: (AuthResult) -> Unit){
        onCompletion(authRepository.signUpWithEmail(email = email, password = password))
    }
}