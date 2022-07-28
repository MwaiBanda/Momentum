package com.mwaibanda.momentum.domain.usecase

import com.mwaibanda.momentum.domain.repository.UserRepository

class UpdateUserFullnameUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, fullname: String){
        userRepository.updateUserFullname(userId = userId, fullname = fullname)

    }
}