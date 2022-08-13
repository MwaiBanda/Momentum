package com.mwaibanda.momentum.android.presentation.payment

import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel.ToggleLabel.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PaymentSummaryContentViewModelTest {
    private lateinit var sut: PaymentSummaryContentViewModel

    @BeforeEach
    fun setUp() {
        sut = PaymentSummaryContentViewModel()
    }

    @Test
    fun `test Selected Labels`(){
        sut.processToggle(true, OFFERING)
        sut.processToggle(true, MISSIONS)
        assertEquals(OFFERING, sut.selectedLabels[0])
        sut.processToggle(false, OFFERING)
        assertEquals(MISSIONS, sut.selectedLabels[0])

    }
    @Test
    fun `test set initial total set to first toggle`()  {
        sut.totalAmount = "44"
        sut.processToggle(isActive = true, type =  MISSIONS)
        assertEquals("44", sut.missionsAmount)
        sut.processToggle(false, MISSIONS)
        assertEquals("0", sut.missionsAmount)

    }

    @Test
    fun `test subtracting totals between two toggles`() {
        sut.totalAmount = "10"
        sut.processToggle(isActive = true, type = OFFERING)
        assertEquals("10", sut.offeringAmount)
        sut.processToggle(isActive = true, type = TITHE)
        sut.titheAmount = "7"
        sut.processAmount(amount = sut.titheAmount, type = TITHE)
        assertEquals("3", sut.offeringAmount)
        val res = sut.titheAmount.toInt() + sut.offeringAmount.toInt()
        assertEquals(res, sut.totalAmount.toInt())
    }

    @Test
    fun `test adding totals between two toggles`() {
        sut.totalAmount = "10"
        sut.processToggle(isActive = true, type = OFFERING)
        assertEquals("10", sut.offeringAmount)
        sut.processToggle(isActive = true, type = TITHE)
        sut.titheAmount = "7"
        sut.processAmount(amount = sut.titheAmount, type = TITHE)
        assertEquals("3", sut.offeringAmount)
        sut.offeringAmount = "6"
        sut.processAmount(amount = sut.offeringAmount, type = OFFERING)
        assertEquals("4", sut.titheAmount)
        val res = sut.titheAmount.toInt().plus(sut.offeringAmount.toInt())
        assertEquals(res, sut.totalAmount.toInt())
    }
}