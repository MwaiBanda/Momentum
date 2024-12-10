package com.mwaibanda.momentum.android.presentation.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.domain.controller.LocalDefaultsController
import com.mwaibanda.momentum.utils.MultiplatformConstants
import io.github.mwaibanda.authentication.utils.AuthResult.Failure
import io.github.mwaibanda.authentication.utils.AuthResult.Success
import io.github.mwaibanda.authentication.utils.DefaultAuthResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AuthViewModel(
    private val authController: AuthController,
    private val localDefaultsController: LocalDefaultsController
) : ViewModel() {
    var currentUser: User? by mutableStateOf(null)


    fun checkAndSignIn() {
        authController.checkAuthAndSignAsGuest { res ->
            when (res) {
                is Success -> {
                    currentUser = User(
                        id = res.data?.uid ?: "",
                        email = res.data?.email,
                        isGuest = res.data?.isAnonymous ?: false
                    )
                }
                is Failure -> {
                    Log.d("Auth/Failure", res.message ?: "")
                }

                else -> {}
            }
            Log.d("Auth", "User {id: ${res.data?.uid}, isGuest: ${res.data?.isAnonymous}}")
        }
    }
    fun resetPassword(email: String, onCompletion: () -> Unit = {}) {
        authController.resetPassword(email) {
            when(it) {
                is DefaultAuthResult.Failure -> {
                    Log.d("Auth/Failure", it.message ?: "")
                }

                is DefaultAuthResult.Success -> {
                    onCompletion()
                }
            }
        }
    }

    fun signIn(email: String, password: String, onCompletion: () -> Unit = {}) {
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
                    localDefaultsController.setString(
                        key = MultiplatformConstants.EMAIL,
                        value = email
                    )
                    localDefaultsController.setString(
                        key = MultiplatformConstants.PASSWORD,
                        value = password
                    )
                    onCompletion()
                }

                else -> {}
            }
        }
    }

    fun signUp(email: String, password: String, onCompletion: () -> Unit = {}) {
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

                else -> {}
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

                else -> {}
            }
        }
    }

    fun getCurrentUser() {
        authController.getCurrentUser { res ->
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

                else -> {}
            }
        }
    }

    fun deleteCurrentUser(onCompletion: () -> Unit = {}) {
        currentUser = null
        authController.deleteUser()
        onCompletion()
    }

    fun signOut(onCompletion: () -> Unit = {}) {
        currentUser = null
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
