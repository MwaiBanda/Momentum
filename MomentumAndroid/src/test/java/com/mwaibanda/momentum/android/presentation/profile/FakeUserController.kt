package com.mwaibanda.momentum.android.presentation.profile

import com.mwaibanda.momentum.data.db.MomentumUser
import com.mwaibanda.momentum.domain.controller.UserController
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.utils.DataResponse

class FakeUserController: UserController {
    private val remote = mutableListOf<User>()
    private val local = mutableListOf<MomentumUser>()

    override fun addMomentumUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String,
        onCompletion: () -> Unit
    ) {
       local.add(
           MomentumUser(
               id = (local.count() + 1).toLong(),
               fullname = fullname,
               phone = phone,
               password = password,
               email = email,
               created_on = createdOn,
               user_id = userId
           )
       )
        onCompletion()
    }

    override fun postUser(user: User) {
        remote.add(user)
    }

    override fun getMomentumUserById(userId: String, onCompletion: (MomentumUser?) -> Unit) {
        onCompletion(local.first { it.user_id == userId })
    }

    override fun getUser(userId: String, onCompletion: (DataResponse<User>) -> Unit) {
        val index = remote.indexOfFirst { it.userId == userId }
        onCompletion(DataResponse.Success(remote[index]))
    }

    override fun updateMomentumUserFullnameByUserId(
        userId: String,
        fullname: String,
        onCompletion: () -> Unit
    ) {
        val index = local.indexOfFirst { it.user_id == userId }
        val user = local[index]
        local.set(
            index = index,
            MomentumUser(
                id = user.id,
                fullname = fullname,
                phone = user.phone,
                password = user.password,
                email = user.email,
                created_on = user.created_on,
                user_id = user.user_id
            )
        )
        onCompletion()
    }

    override fun updateUser(user: User, onCompletion: (User) -> Unit) {
        TODO("Not yet implemented")
    }

     fun updateUserFullname(userID: String, fullname: String) {
        val index = remote.indexOfFirst { it.userId == userID }
        val user = remote[index]
        remote.set(
            index = index,
            User(
                fullname = fullname,
                email = user.email,
                phone = user.phone,
                userId = user.userId,
                createdOn = user.createdOn
            )
        )
    }

    override fun updateMomentumUserEmailByUserId(
        userId: String,
        email: String,
        onCompletion: () -> Unit
    ) {
        val index = local.indexOfFirst { it.user_id == userId }
        val user = local[index]
        local.set(
            index = index,
            MomentumUser(
                id = user.id,
                fullname = user.fullname,
                phone = user.phone,
                password = user.password,
                email = email,
                created_on = user.created_on,
                user_id = user.user_id
            )
        )
        onCompletion()
    }

     fun updateUserEmail(userID: String, email: String) {
        val index = remote.indexOfFirst { it.userId == userID }
        val user = remote[index]
        remote.set(
            index = index,
            User(
                fullname = user.fullname,
                email = email,
                phone = user.phone,
                userId = user.userId,
                createdOn = user.createdOn
            )
        )
    }

    override fun updateMomentumUserPasswordUserId(
        userId: String,
        password: String,
        onCompletion: () -> Unit
    ) {
        val index = local.indexOfFirst { it.user_id == userId }
        val user = local[index]
        local.set(
            index = index,
            MomentumUser(
                id = user.id,
                fullname = user.fullname,
                phone = user.phone,
                password = password,
                email = user.email,
                created_on = user.created_on,
                user_id = user.user_id
            )
        )
        onCompletion()
    }

    override fun updateMomentumUserPhoneByUserId(
        userId: String,
        phone: String,
        onCompletion: () -> Unit
    ) {
        val index = local.indexOfFirst { it.user_id == userId }
        val user = local[index]
        local.set(
            index = index,
            MomentumUser(
                id = user.id,
                fullname = user.fullname,
                phone = phone,
                password = user.password,
                email = user.email,
                created_on = user.created_on,
                user_id = user.user_id
            )
        )
        onCompletion()
    }

     fun updatePhoneByUserId(userId: String, phone: String) {
        val index = remote.indexOfFirst { it.userId == userId }
        val user = remote[index]
        remote.set(
            index = index,
            User(
                fullname = user.fullname,
                email = user.email,
                phone = phone,
                userId = user.userId,
                createdOn = user.createdOn
            )
        )
    }

    override fun deleteMomentumUserByUserId(userId: String, onCompletion: () -> Unit) {
        val index = local.indexOfFirst { it.user_id == userId }
        local.removeAt(index)
        onCompletion()
    }

    override fun deleteUser(userID: String, onCompletion: () -> Unit) {
        val index = remote.indexOfFirst { it.userId == userID }
        remote.removeAt(index)
    }
}