package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.EmailAuthProvider
import dev.gitlive.firebase.auth.FirebaseAuth

internal class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun signInWithEmail(email: String, password: String): UserResponse {
        if (firebaseAuth.currentUser != null && (firebaseAuth.currentUser?.isAnonymous == true))
            deleteUser()
        val res = firebaseAuth.signInWithEmailAndPassword(email = email, password = password).user
        return UserResponse(
            uid = res?.uid ?: "",
            email = res?.email,
            isAnonymous = res?.isAnonymous ?: false
        )
    }

    override suspend fun signUpWithEmail(email: String, password: String): UserResponse {
        val credential = EmailAuthProvider.credential(email = email, password = password)
        if (firebaseAuth.currentUser == null)
            firebaseAuth.signInAnonymously()
        val res = firebaseAuth.currentUser?.linkWithCredential(credential)?.user
        return UserResponse(
            uid = res?.uid ?: "",
            email = res?.email,
            isAnonymous = res?.isAnonymous ?: false
        )
    }

    override suspend fun signInAsGuest(): UserResponse {
        val res = firebaseAuth.signInAnonymously().user
        return UserResponse(
            uid = res?.uid ?: "",
            email = res?.email,
            isAnonymous = res?.isAnonymous ?: false
        )
    }

    override suspend fun isUserSignedIn(): Boolean {
        return (firebaseAuth.currentUser != null)
    }

    override suspend fun getCurrentUser(): UserResponse {
        val res = firebaseAuth.currentUser
        return UserResponse(
            uid = res?.uid ?: "",
            email = res?.email,
            isAnonymous = res?.isAnonymous ?: false
        )
    }

    override suspend fun deleteUser() {
        firebaseAuth.currentUser?.delete()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}