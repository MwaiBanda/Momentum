package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.MomentumUser
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.utils.DataResponse

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
    fun postUser(user: User)
    fun updateUser(user: User, onCompletion: (User) -> Unit)

    fun getMomentumUserById(userId: String, onCompletion: (MomentumUser?) -> Unit)
    fun getUser(userId: String, onCompletion: (DataResponse<User>) -> Unit)
    fun updateMomentumUserFullnameByUserId(
        userId: String,
        fullname: String,
        onCompletion: () -> Unit
    )
    fun updateMomentumUserEmailByUserId(
        userId: String,
        email: String,
        onCompletion: () -> Unit
    )
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
    fun deleteMomentumUserByUserId(userId: String, onCompletion: () -> Unit)
    fun deleteUser(userID: String, onCompletion: () -> Unit)
}