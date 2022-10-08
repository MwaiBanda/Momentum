package com.mwaibanda.momentum.android.presentation.payment

import app.cash.turbine.test
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.models.Transaction
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PaymentViewModelTest {
    private lateinit var sut: PaymentViewModel
    private lateinit var paymentController: PaymentController

    @BeforeEach
    fun setUp() {
        paymentController = FakePaymentController()
        sut = PaymentViewModel(paymentController)
    }

    @Test
    fun `test checkout`() = runBlocking {
        sut.checkout(
            Transaction(
                fullname = "Mwai Banda",
                email = "mwai.developer@gmail.com",
                phone = "2190000000",
                description = "",
                amount = 100
            )
        )
        sut.paymentResponse.test {
            val emission = awaitItem()
            assertEquals("Mwai Banda", emission?.customer ?: "")
        }
    }
}