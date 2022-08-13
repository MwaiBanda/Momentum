package com.mwaibanda.momentum.android.presentation.offer

import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OfferViewModelTest {
    private lateinit var sut: OfferViewModel

    @BeforeEach
    fun setUp() {
        sut = OfferViewModel()
    }

    @Test
    fun `test number passed through processing has no formatting`() = runBlocking {
        sut.processInput('3')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('0')

        sut.number.test {
            val emission = awaitItem()
            assertEquals("3000", emission)
        }
    }

    @Test
    fun `test display number passed through processing is properly formatted`() = runBlocking {
        sut.processInput('3')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('0')

        sut.displayNumber.test {
            val emission = awaitItem()
            assertEquals("3,000", emission)
        }
    }

    @Test
    fun `test decimal number passed through processing has no formatting`() = runBlocking {
        sut.processInput('3')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('•')
        sut.processInput('9')
        sut.processInput('9')

        sut.number.test {
            val emission = awaitItem()
            assertEquals("3000.99", emission)
        }
    }

    @Test
    fun `test decimal display number passed through processing is properly formatted`() = runBlocking {
        sut.processInput('3')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('•')
        sut.processInput('9')
        sut.processInput('9')

        sut.displayNumber.test {
            val emission = awaitItem()
            assertEquals("3,000.99", emission)
        }
    }
    @Test
    fun `test decimal display number backspace passed through processing is properly formatted`() = runBlocking {
        sut.processInput('3')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('•')
        sut.processInput('9')
        sut.processInput('9')
        sut.processInput('<')
        sut.processInput('<')

        sut.displayNumber.test {
            val emission = awaitItem()
            assertEquals("3,000.00", emission)
        }
    }
    @Test
    fun `test decimal number backspace passed through processing returns to non decimal`() = runBlocking {
        sut.processInput('3')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('•')
        sut.processInput('9')
        sut.processInput('9')
        sut.processInput('<')
        sut.processInput('<')
        sut.processInput('<')

        sut.number.test {
            val emission = awaitItem()
            assertEquals("3000", emission)
        }
    }
    @Test
    fun `test decimal display number backspace passed through processing returns to non decimal`() = runBlocking {
        sut.processInput('3')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('0')
        sut.processInput('•')
        sut.processInput('9')
        sut.processInput('9')
        sut.processInput('<')
        sut.processInput('<')
        sut.processInput('<')

        sut.displayNumber.test {
            val emission = awaitItem()
            assertEquals("3,000", emission)
        }
    }
}
