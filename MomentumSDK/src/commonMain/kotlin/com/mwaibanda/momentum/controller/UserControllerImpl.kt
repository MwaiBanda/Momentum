package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumUser
import com.mwaibanda.momentum.domain.controller.UserController
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.usecase.user.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserControllerImpl(driverFactory: DatabaseDriverFactory): UserController, KoinComponent {
    private val postUserUseCase: PostUserUseCase by inject()
    private val getUserUseCase: GetUserUseCase by inject()
    private val updateUserFullnameUseCase: UpdateUserFullnameUseCase by inject()
    private val updateUserEmailUseCase: UpdateUserEmailUseCase by inject()
    private val updateUserPhoneUseCase: UpdateUserPhoneUseCase by inject()
    private val deleteOnlineUserUseCase: DeleteRemoteUserUseCase by inject()
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
            postUserUseCase(user = user)
        }
    }

    override fun getMomentumUserById(userId: String, onCompletion: (MomentumUser?) -> Unit) {
        onCompletion(database.getUserByUserId(userId = userId))
    }

    override fun getUser(userId: String, onCompletion: (User) -> Unit) {
        scope.launch {
            getUserUseCase(userId) {
                onCompletion(it)
            }
        }
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
            updateUserFullnameUseCase(
                userId = userID,
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
            updateUserEmailUseCase(
                userId = userID,
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


    override fun updatePhoneByUserId(userId: String, phone: String) {
        scope.launch {
            updateUserPhoneUseCase(
                userId = userId,
                phone = phone
            )
        }
    }

    override fun deleteMomentumUserByUserId(userId: String, onCompletion: () -> Unit) {
        database.deleteUserByUserId(userId = userId)
        onCompletion()
    }

    override fun deleteUser(userID: String, onCompletion: () -> Unit) {
        scope.launch {
            deleteOnlineUserUseCase(userId = userID)
            onCompletion()
        }
    }
}