package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumUser
import com.mwaibanda.momentum.domain.controller.UserController
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.usecase.user.DeleteRemoteUserUseCase
import com.mwaibanda.momentum.domain.usecase.user.GetUserUseCase
import com.mwaibanda.momentum.domain.usecase.user.PostUserUseCase
import com.mwaibanda.momentum.domain.usecase.user.UpdateUserCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserControllerImpl(driverFactory: DatabaseDriverFactory): UserController, KoinComponent {
    private val postUserUseCase: PostUserUseCase by inject()
    private val getUserUseCase: GetUserUseCase by inject()
    private val updateUserCase: UpdateUserCase by inject()

    private val deleteOnlineUserUseCase: DeleteRemoteUserUseCase by inject()
    private val database = Database(driverFactory)
    private val scope: CoroutineScope = MainScope()
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

    override fun updateUser(user: User, onCompletion: (User) -> Unit) {
        scope.launch {
            updateUserCase(user, onCompletion)
        }
    }

    override fun getMomentumUserById(userId: String, onCompletion: (MomentumUser?) -> Unit) {
        onCompletion(database.getUserByUserId(userId = userId))
    }

    override fun getUser(userId: String, onCompletion: (Result<User>) -> Unit) {
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