package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.MultiplatformConstants
import dev.gitlive.firebase.firestore.FirebaseFirestore

class UserRepositoryImpl(
    private val db: FirebaseFirestore
): UserRepository {
    override suspend fun postUser(user: User) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(user.userId)
            .set(User.serializer(), user, encodeDefaults = true)
    }

    override suspend fun getUser(userID: String) {

    }
}