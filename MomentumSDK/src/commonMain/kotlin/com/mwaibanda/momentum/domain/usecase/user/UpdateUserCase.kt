package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.Result

class UpdateUserCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User, onCompletion: (Result<User>) -> Unit) {
       onCompletion(userRepository.updateUser(user))
    }
}