package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.MultiplatformConstants
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore

class UserRepositoryImpl(
    private val db: FirebaseFirestore
): UserRepository {
    init {
        db.setSettings(persistenceEnabled = false)
    }

    override suspend fun postUser(user: User) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(user.userId)
            .set(User.serializer(), user, encodeDefaults = true)
    }


    override suspend fun getUserPhone(userID: String, phone: String) {
        db.collection(MultiplatformConstants.USERS_COLLECTION)
            .document(userID)
            .set(hashMapOf(PHONE_KEY to phone), merge = true)
    }

    override suspend fun getUser(userID: String) {

    }
    
    companion object {
        const val PHONE_KEY = "phone"
    }

}