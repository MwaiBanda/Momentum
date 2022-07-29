package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.domain.usecase.auth.*
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthControllerImpl: AuthController, KoinComponent {
    private val signInWithEmailUseCase: SignInWithEmailUseCase by inject()
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase by inject()
    private val signInAsGuestUseCase: SignInAsGuestUseCase by inject()
    private val isUserSignedInUseCase: IsUserSignedInUseCase by inject()
    private val deleteUserUseCase: DeleteUserUseCase by inject()
    private val signOutUseCase: SignOutUseCase by inject()
    private val scope = MainScope()

    override fun signInWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult) -> Unit
    ) {
        scope.launch {
           signInWithEmailUseCase(email = email, password = password) {
               onCompletion(it)
           }
        }
    }

    override fun signUpWithEmail(
        email: String,
        password: String,
        onCompletion: (AuthResult) -> Unit
    ) {
        scope.launch {
            signUpWithEmailUseCase(email = email, password = password) {
                onCompletion(it)
            }
        }
    }

    override fun signInAsGuest(onCompletion: (AuthResult) -> Unit) {
        scope.launch {
            signInAsGuestUseCase {
                onCompletion(it)
            }
        }
    }

    override fun isUserSignedIn(onCompletion: (Boolean) -> Unit)  {
        scope.launch {
            onCompletion(isUserSignedInUseCase())
        }
    }

    override fun checkAuthAndSignAsGuest(onCompletion: (AuthResult) -> Unit) {
        isUserSignedIn { isSignedIn ->
            if (isSignedIn.not()) {
                signInAsGuest {
                    onCompletion(it)
                }
            }
        }
    }

    override fun deleteUser() {
        scope.launch {
            deleteUserUseCase()
        }
    }

    override fun signOut(){
        scope.launch {
            signOutUseCase()
        }
    }
}