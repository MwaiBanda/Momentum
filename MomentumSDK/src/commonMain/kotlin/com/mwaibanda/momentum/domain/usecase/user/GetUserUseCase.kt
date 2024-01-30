package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.DataResponse

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, onCompletion: (DataResponse<User>) -> Unit) {
        onCompletion(userRepository.fetchUser(userId))
    }
}