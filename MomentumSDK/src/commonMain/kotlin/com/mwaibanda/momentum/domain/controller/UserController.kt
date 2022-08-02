package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.MomentumUser
import com.mwaibanda.momentum.domain.models.UserRequest

interface UserController {
    fun addMomentumUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String,
        onCompletion: () -> Unit
    )
    fun postUser(userRequest: UserRequest)
    fun getMomentumUserById(userId: String, onCompletion: (MomentumUser?) -> Unit)
    fun updateMomentumUserFullnameByUserId(
        userId: String,
        fullname: String,
        onCompletion: () -> Unit
    )
    fun updateUserFullname(userID: String, fullname: String)
    fun updateMomentumUserEmailByUserId(
        userId: String,
        email: String,
        onCompletion: () -> Unit
    )
    fun updateUserEmail(userID: String, email: String)
    fun updateMomentumUserPasswordUserId(
        userId: String,
        password: String,
        onCompletion: () -> Unit
    )
    fun updateMomentumUserPhoneByUserId(
        userId: String,
        phone: String,
        onCompletion: () -> Unit
    )
    fun updatePhoneByUserId(userId: String, phone: String)
    fun deleteMomentumUserByUserId(userId: String, onCompletion: () -> Unit)
    fun deleteUser(userID: String, onCompletion: () -> Unit)
}