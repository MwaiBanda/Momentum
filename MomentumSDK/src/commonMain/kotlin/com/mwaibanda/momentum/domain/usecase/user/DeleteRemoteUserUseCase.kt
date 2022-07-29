package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.repository.UserRepository

class DeleteRemoteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String){
        userRepository.deleteUser(userId = userId)
    }
}