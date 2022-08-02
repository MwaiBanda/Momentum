package com.mwaibanda.momentum.domain.usecase.auth

import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.domain.repository.AuthRepository

class SignInAsGuestUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(onCompletion: (UserResponse) -> Unit){
        onCompletion(authRepository.signInAsGuest())
    }
}