package com.mwaibanda.momentum.android.presentation.auth

import com.mwaibanda.momentum.domain.controller.AuthController
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AuthViewModelTest {
    private lateinit var authController: AuthController
    private lateinit var sut: AuthViewModel

    @BeforeEach
    fun setUp() {
        authController = FakeAuthController()
        sut = AuthViewModel(authController)
    }

    @Test
    fun `test signUp`() {
        sut.getCurrentUser()
        assertEquals(null, sut.currentUser)
        sut.signUp(
            email = "admin@test.com",
            password = "*****"
        )
        assertEquals("admin@test.com", sut.currentUser?.email)
    }

    @Test
    fun `test signIn`() {
        sut.getCurrentUser()
        assertEquals(null, sut.currentUser)
        sut.signIn(
            email = "test@admin.com",
            password = "*****"
        )
        assertEquals("test@admin.com", sut.currentUser?.email)
    }

    @Test
    fun `test signIn as a guest`() {
        sut.getCurrentUser()
        assertEquals(null, sut.currentUser)
        sut.signInAsGuest()
        assertEquals("guest@momentum.com", sut.currentUser?.email)
    }

    @Test
    fun `test check user and signIn as a guest`() {
        sut.getCurrentUser()
        assertEquals(null, sut.currentUser)
        sut.checkAndSignIn()
        assertEquals("guest@momentum.com", sut.currentUser?.email)
    }

    @Test
    fun `test signOut`() {
        sut.getCurrentUser()
        assertEquals(null, sut.currentUser)
        sut.signInAsGuest()
        assertEquals("guest@momentum.com", sut.currentUser?.email)
        sut.signOut()
        assertEquals(null, sut.currentUser)
    }

}