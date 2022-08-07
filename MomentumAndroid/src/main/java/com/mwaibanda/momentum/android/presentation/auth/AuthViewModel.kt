package com.mwaibanda.momentum.android.presentation.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.utils.Result.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AuthViewModel(
    private val authController: AuthController
) : ViewModel() {
    var currentUser: User? by mutableStateOf(null)

    init { checkAndSignIn() }

    private fun checkAndSignIn() {
        authController.checkAuthAndSignAsGuest { res ->
            when (res) {
                is Failure -> {
                    Log.d("Auth/Failure", res.message ?: "")
                }
                is Success -> {
                    currentUser = User(
                        id = res.data?.uid ?: "",
                        email = res.data?.email,
                        isGuest = res.data?.isAnonymous ?: false
                    )
                }
            }
            Log.d("Auth", "User {id: ${res.data?.uid}, isGuest: ${res.data?.isAnonymous}}")
        }
    }

    fun signIn(email: String, password: String, onCompletion: () -> Unit) {
        authController.signInWithEmail(email = email, password = password) { res ->
            when (res) {
                is Failure -> {
                    Log.d("Auth/Failure", res.message ?: "")
                }
                is Success -> {
                    currentUser = User(
                        id = res.data?.uid ?: "",
                        email = res.data?.email,
                        isGuest = res.data?.isAnonymous ?: false
                    )
                    onCompletion()
                }
            }
        }
    }

    fun signUp(email: String, password: String, onCompletion: () -> Unit) {
        authController.signUpWithEmail(email = email, password = password) { res ->
            when (res) {
                is Failure -> {
                    Log.d("Auth/Failure", res.message ?: "")
                }
                is Success -> {
                    currentUser = User(
                        id = res.data?.uid ?: "",
                        email = res.data?.email,
                        isGuest = res.data?.isAnonymous ?: false
                    )
                    onCompletion()
                }
            }
            Log.d("Auth", "User {id: ${res.data?.uid}, isGuest: ${res.data?.isAnonymous}}")
        }
    }

    fun signInAsGuest() {
        authController.signInAsGuest { res ->
            when (res) {
                is Failure -> {
                    Log.d("Auth/Failure", res.message ?: "")
                }
                is Success -> {
                    currentUser = User(
                        id = res.data?.uid ?: "",
                        email = res.data?.email,
                        isGuest = res.data?.isAnonymous ?: false
                    )
                }
            }
        }
    }

    fun deleteCurrentUser(onCompletion: () -> Unit){
        authController.deleteUser()
        onCompletion()
    }
    fun signOut(onCompletion: () -> Unit) {
        authController.signOut()
        onCompletion()
    }

    fun getCurrentDate(): String {
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        return date.format(formatter)
    }

    data class User(
        val id: String,
        val email: String?,
        val isGuest: Boolean
    )
}
