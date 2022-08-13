package com.mwaibanda.momentum.android.presentation.profie

import com.mwaibanda.momentum.data.db.MomentumUser
import com.mwaibanda.momentum.domain.controller.UserController
import com.mwaibanda.momentum.domain.models.User

class FakeUserController: UserController {
    override fun addMomentumUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String,
        onCompletion: () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun postUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun getMomentumUserById(userId: String, onCompletion: (MomentumUser?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: String, onCompletion: (User) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun updateMomentumUserFullnameByUserId(
        userId: String,
        fullname: String,
        onCompletion: () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateUserFullname(userID: String, fullname: String) {
        TODO("Not yet implemented")
    }

    override fun updateMomentumUserEmailByUserId(
        userId: String,
        email: String,
        onCompletion: () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateUserEmail(userID: String, email: String) {
        TODO("Not yet implemented")
    }

    override fun updateMomentumUserPasswordUserId(
        userId: String,
        password: String,
        onCompletion: () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateMomentumUserPhoneByUserId(
        userId: String,
        phone: String,
        onCompletion: () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updatePhoneByUserId(userId: String, phone: String) {
        TODO("Not yet implemented")
    }

    override fun deleteMomentumUserByUserId(userId: String, onCompletion: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userID: String, onCompletion: () -> Unit) {
        TODO("Not yet implemented")
    }
}