package com.mwaibanda.momentum.domain.usecase

import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.AuthResult

class SignInWithEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, onCompletion: (AuthResult) -> Unit){
        onCompletion(authRepository.signInWithEmail(email = email, password = password))
    }
}