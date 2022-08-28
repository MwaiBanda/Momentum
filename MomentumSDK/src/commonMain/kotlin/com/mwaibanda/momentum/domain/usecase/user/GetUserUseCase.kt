package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.Result

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, onCompletion: (Result<User>) -> Unit) {
        onCompletion(userRepository.getUser(userId))
    }
}