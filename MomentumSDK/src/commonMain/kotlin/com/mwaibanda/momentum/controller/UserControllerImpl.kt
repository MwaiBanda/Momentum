package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumUser
import com.mwaibanda.momentum.domain.controller.UserController
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserControllerImpl(driverFactory: DatabaseDriverFactory): UserController, KoinComponent {
    private val userRepository: UserRepository by inject()
    private val database = Database(driverFactory)
    private val scope = MainScope()

    override fun addMomentumUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String,
        onCompletion: () -> Unit
    ) {
        database.insertUser(
            fullname  = fullname,
            phone = phone,
            password = password,
            email = email,
            createdOn = createdOn,
            userId = userId
        )
        onCompletion()
    }

    override fun postUser(user: User) {
        scope.launch {
            userRepository.postUser(user = user)
        }
    }

    override fun getMomentumUserById(userId: String, onCompletion: () -> Unit): MomentumUser {
        val user = database.getUserByUserId(userId = userId)
        onCompletion()
        return user
    }

    override fun updateMomentumUserFullnameByUserId(
        userId: String,
        fullname: String,
        onCompletion: () -> Unit
    ) {
        database.updateUserFullnameByUserId(
            userId = userId,
            fullname = fullname
        )
        onCompletion()
    }

    override fun updateUserFullname(userID: String, fullname: String) {
        scope.launch {
            userRepository.updateUserFullname(
                userID = userID,
                fullname = fullname
            )
        }
    }

    override fun updateMomentumUserEmailByUserId(
        userId: String,
        email: String,
        onCompletion: () -> Unit
    ) {
        database.updateUserEmailByUserId(
            userId = userId,
            email = email
        )
        onCompletion()
    }

    override fun updateUserEmail(userID: String, email: String) {
        scope.launch {
            userRepository.updateUserEmail(
                userID = userID,
                email = email
            )
        }
    }

    override fun updateMomentumUserPasswordUserId(
        userId: String,
        password: String,
        onCompletion: () -> Unit
    ) {
        database.updateUserPasswordUserId(
            userId = userId,
            password = password
        )
        onCompletion()
    }

    override fun updateMomentumUserPhoneByUserId(
        userId: String,
        phone: String,
        onCompletion: () -> Unit
    ) {
        database.updateUserPhoneByUserId(
            userId = userId,
            phone = phone
        )
        onCompletion()
    }

    override fun updateUserPhone(userId: String, phone: String) {
        scope.launch {
            userRepository.updateUserPhone(
                userID = userId,
                phone = phone
            )
        }
    }

    override fun updatePhoneByUserId(userId: String, phone: String) {
        scope.launch {
            userRepository.updateUserPhone(
                userID = userId,
                phone = phone
            )
        }
    }

    override fun deleteMomentumUserByUserId(userId: String, onCompletion: () -> Unit) {
        database.deleteUserByUserId(userId = userId)
        onCompletion()
    }

    override fun deleteUser(userID: String) {
        scope.launch {
            userRepository.deleteUser(userID = userID)
        }
    }
}