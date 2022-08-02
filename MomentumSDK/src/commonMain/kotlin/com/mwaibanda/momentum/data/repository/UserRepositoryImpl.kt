package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.models.UserRequest
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.MultiplatformConstants
import dev.gitlive.firebase.firestore.FirebaseFirestore

class UserRepositoryImpl(
    private val db: FirebaseFirestore
): UserRepository {
    init {
        db.setSettings(persistenceEnabled = false)
    }

    override suspend fun postUser(userRequest: UserRequest) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(userRequest.userId)
            .set(UserRequest.serializer(), userRequest, encodeDefaults = true)
    }

    override suspend fun updateUserEmail(userId: String, email: String) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(userId)
            .set(hashMapOf(EMAIL_KEY to email), merge = true)
    }

    override suspend fun updateUserPhone(userId: String, phone: String) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(userId)
            .set(hashMapOf(PHONE_KEY to phone), merge = true)
    }



    override suspend fun updateUserFullname(userId: String, fullname: String) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(userId)
            .set(hashMapOf(FULLNAME_KEY to fullname), merge = true)
    }

    override suspend fun deleteUser(userId: String) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(userId)
            .delete()
    }

    companion object {
        const val EMAIL_KEY = "email"
        const val PHONE_KEY = "phone"
        const val FULLNAME_KEY = "fullname"
    }
}