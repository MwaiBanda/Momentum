package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.repository.UserRepository

class UpdateUserEmailUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, email: String) {
        userRepository.updateUserEmail(userId = userId, email = email)
    }
}