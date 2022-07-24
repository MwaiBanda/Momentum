package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.EmailAuthProvider
import dev.gitlive.firebase.auth.FirebaseAuth

internal class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        if (firebaseAuth.currentUser != null)
            firebaseAuth.currentUser?.delete()
        return firebaseAuth.signInWithEmailAndPassword(email = email, password = password)
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        val credential = EmailAuthProvider.credential(email = email, password = password)
        if (firebaseAuth.currentUser == null)
            firebaseAuth.signInAnonymously()
        return firebaseAuth.currentUser?.linkWithCredential(credential)!!
    }

    override suspend fun signInAsGuest(): AuthResult {
        return firebaseAuth.signInAnonymously()
    }
}