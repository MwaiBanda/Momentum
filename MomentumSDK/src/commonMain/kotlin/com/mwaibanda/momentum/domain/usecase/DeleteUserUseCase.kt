package com.mwaibanda.momentum.domain.usecase

import com.mwaibanda.momentum.domain.repository.UserRepository

class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String){
        userRepository.deleteUser(userId = userId)
    }
}