package com.mwaibanda.momentum.domain.usecase.user

import com.mwaibanda.momentum.domain.repository.UserRepository

class UpdateUserPhoneUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, phone: String){
        userRepository.updateUserPhone(userId = userId, phone = phone)
    }
}