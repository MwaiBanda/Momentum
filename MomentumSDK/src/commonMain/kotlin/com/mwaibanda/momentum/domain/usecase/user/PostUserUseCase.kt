package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.models.UserRequest
import com.mwaibanda.momentum.domain.repository.UserRepository

class PostUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userRequest: UserRequest) {
        userRepository.postUser(userRequest = userRequest)
    }
}