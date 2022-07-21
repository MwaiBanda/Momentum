package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.FirebaseAuth

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return firebaseAuth.signInWithEmailAndPassword(email = email, password = password)
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        return firebaseAuth.createUserWithEmailAndPassword(email = email, password = password)
    }

    override suspend fun signInAsGuest(): AuthResult {
        return firebaseAuth.signInAnonymously()
    }
}