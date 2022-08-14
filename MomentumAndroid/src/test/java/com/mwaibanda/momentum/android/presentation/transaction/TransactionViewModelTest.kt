package com.mwaibanda.momentum.android.presentation.transaction

import app.cash.turbine.test
import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TransactionViewModelTest {
    private lateinit var sut: TransactionViewModel
    private lateinit var transactionController: TransactionController

    @BeforeEach
    fun setUp() {
        transactionController = FakeTransactionController()
        sut = TransactionViewModel(transactionController)
    }

    @Test
    fun `test add transaction`() = runBlocking {
        sut.addTransaction(
            description = "test added transaction",
            date = "08/13/2022",
            amount = 44.44,
            isSeen = false
        )
        sut.getAllTransactions()
        sut.transactions.test {
            val emissions = awaitItem()
            assertEquals(1, emissions[0].id)
            assertEquals(44.44, emissions[0].amount)
            assertEquals("test added transaction", emissions[0].description)
        }
    }

    @Test
    fun `test delete all transactions`() = runBlocking {
        sut.addTransaction(
            description = "test added transaction",
            date = "08/13/2022",
            amount = 44.44,
            isSeen = false
        )
        sut.getAllTransactions()
        sut.deleteAllTransactions()
        sut.getAllTransactions()
        sut.transactions.test {

            val emission = awaitItem()
            assertEquals(emptyList<MomentumTransaction>(), emission)

        }
    }

    @Test
    fun `test delete transaction by Id`() = runBlocking {
        sut.addTransaction(
            description = "first added transaction",
            date = "08/13/2022",
            amount = 44.44,
            isSeen = false
        )
        sut.addTransaction(
            description = "second added transaction",
            date = "08/13/2022",
            amount = 55.99,
            isSeen = false
        )
        sut.deleteTransactionById(1)
        sut.getAllTransactions()
        sut.transactions.test {

            val emissions = awaitItem()
            assertEquals(2, emissions[0].id)
            assertEquals(55.99, emissions[0].amount)
            assertEquals("second added transaction", emissions[0].description)
        }
    }

}