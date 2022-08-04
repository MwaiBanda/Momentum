package com.mwaibanda.momentum.domain.usecase.auth

import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.domain.repository.AuthRepository
import com.mwaibanda.momentum.utils.Result

class SignUpWithEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        onCompletion: (Result<UserResponse>) -> Unit
    ) {
        onCompletion(authRepository.signUpWithEmail(email = email, password = password))
    }
}