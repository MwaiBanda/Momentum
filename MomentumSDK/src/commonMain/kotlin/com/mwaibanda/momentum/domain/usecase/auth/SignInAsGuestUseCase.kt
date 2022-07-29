package com.mwaibanda.momentum.domain.usecase.auth

import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.AuthResult

class SignInAsGuestUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(onCompletion: (AuthResult) -> Unit){
        onCompletion(authRepository.signInAsGuest())
    }
}