package com.mwaibanda.momentum.domain.usecase.auth

import com.mwaibanda.momentum.domain.repository.AuthRepository

class SignOutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(){
        authRepository.signOut()
    }
}