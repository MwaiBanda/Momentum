package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.models.UserResponse
import com.mwaibanda.momentum.domain.repository.AuthRepository
import com.mwaibanda.momentum.utils.Result
import dev.gitlive.firebase.auth.EmailAuthProvider
import dev.gitlive.firebase.auth.FirebaseAuth

internal class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithEmail(email: String, password: String): Result<UserResponse> {
        if (firebaseAuth.currentUser != null && (firebaseAuth.currentUser?.isAnonymous == true))
            deleteUser()
        return try {
            val res =
                firebaseAuth.signInWithEmailAndPassword(email = email, password = password).user
            Result.Success(
                UserResponse(
                    uid = res?.uid ?: "",
                    email = res?.email,
                    isAnonymous = res?.isAnonymous ?: false
                )
            )
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): Result<UserResponse> {
        val credential = EmailAuthProvider.credential(email = email, password = password)
        if (firebaseAuth.currentUser == null)
            firebaseAuth.signInAnonymously()
        return try {

            val res = firebaseAuth.currentUser?.linkWithCredential(credential)?.user
            Result.Success(
                UserResponse(
                    uid = res?.uid ?: "",
                    email = res?.email,
                    isAnonymous = res?.isAnonymous ?: false
                )
            )
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun signInAsGuest(): Result<UserResponse> {
        return try {
            val res = firebaseAuth.signInAnonymously().user
            Result.Success(
                UserResponse(
                    uid = res?.uid ?: "",
                    email = res?.email,
                    isAnonymous = res?.isAnonymous ?: false
                )
            )
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun isUserSignedIn(): Boolean {
        return (firebaseAuth.currentUser != null)
    }

    override suspend fun getCurrentUser(): Result<UserResponse> {
        return try {
            val res = firebaseAuth.currentUser
            Result.Success(
                UserResponse(
                    uid = res?.uid ?: "",
                    email = res?.email,
                    isAnonymous = res?.isAnonymous ?: false
                )
            )
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun deleteUser() {
        firebaseAuth.currentUser?.delete()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}