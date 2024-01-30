package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.DataResponse

class UpdateUserCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User, onCompletion: (DataResponse<User>) -> Unit) {
       onCompletion(userRepository.updateUser(user))
    }
}