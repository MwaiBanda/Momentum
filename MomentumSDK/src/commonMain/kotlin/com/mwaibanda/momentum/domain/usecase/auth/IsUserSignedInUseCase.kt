package com.mwaibanda.momentum.domain.usecase.auth

import com.mwaibanda.momentum.domain.repository.AuthRepository

class IsUserSignedInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserSignedIn()
    }
}