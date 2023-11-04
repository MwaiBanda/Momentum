package com.mwaibanda.momentum.android.presentation.profile

import com.mwaibanda.momentum.android.presentation.auth.FakeLocalDefaultsController
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.BILLING_INFO
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.CONTACT_INFO
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.FEEDBACK
import com.mwaibanda.momentum.domain.controller.BillingAddressController
import com.mwaibanda.momentum.domain.controller.LocalDefaultsController
import com.mwaibanda.momentum.domain.controller.UserController
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ProfileViewModelTest {
    private lateinit var userController: UserController
    private lateinit var billingAddressController: BillingAddressController
    private lateinit var localDefaults: LocalDefaultsController
    private lateinit var sut: ProfileViewModel

    @BeforeEach
    fun setUp() {
        userController = FakeUserController()
        billingAddressController = FakeBillingAddressController()
        localDefaults = FakeLocalDefaultsController()
        sut = ProfileViewModel(userController, billingAddressController, localDefaults)
    }

    @Test
    fun `test card toggle`() {
        assertFalse(sut.isBillingExpanded)
        sut.cardToggle(BILLING_INFO)
        assertTrue(sut.isBillingExpanded)
        sut.cardToggle(BILLING_INFO)
        assertFalse(sut.isBillingExpanded)
    }


    @Test
    fun `test close cards`() {
        assertFalse(sut.isContactExpanded)
        assertFalse(sut.isBillingExpanded)
        assertFalse(sut.isFeedbackExpanded)
        sut.cardToggle(CONTACT_INFO)
        sut.cardToggle(BILLING_INFO)
        sut.cardToggle(FEEDBACK)
        assertTrue(sut.isContactExpanded)
        assertTrue(sut.isBillingExpanded)
        assertTrue(sut.isFeedbackExpanded)
        sut.closeCards(CONTACT_INFO, BILLING_INFO, FEEDBACK)
        assertFalse(sut.isContactExpanded)
        assertFalse(sut.isBillingExpanded)
        assertFalse(sut.isFeedbackExpanded)
    }

    @Test
    fun `test add user`() {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.getContactInformation(userId = "M1001-B00")
        assertEquals("Mwai Banda", sut.fullname)
    }

    @Test
    fun `test fullname update`() {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.fullname = "Mwai"
        sut.updateFullname(userId = "M1001-B00")
        sut.fullname = "Mwai Banda"
        sut.getContactInformation(userId = "M1001-B00")
        assertEquals("Mwai", sut.fullname)
    }

    @Test
    fun `test phone update`() {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.phone = "2190001111"
        sut.updatePhone(userId = "M1001-B00")
        sut.phone = "2191111111"
        sut.getContactInformation(userId = "M1001-B00")
        assertEquals("2190001111", sut.phone)
    }

    @Test
    fun `test password update`() {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.password = "*****093"
        sut.updatePassword(userId = "M1001-B00")
        sut.password = "*******"
        sut.getContactInformation(userId = "M1001-B00")
        assertEquals("*****093", sut.password)
    }

    @Test
    fun `test email update`() {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.email = "test@admin.com"
        sut.updateEmail(userId = "M1001-B00")
        sut.email = "mwai.developer@gmail.com"
        sut.getContactInformation(userId = "M1001-B00")
        assertEquals("test@admin.com", sut.email)
    }

    @Test
    fun `test street-address update`()  {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.streetAddress = "1st first St"
        sut.updateStreetAddress(userId = "M1001-B00")
        sut.streetAddress = "2nd second St"
        sut.getBillingInformation(userId = "M1001-B00")
        assertEquals( "1st first St", sut.streetAddress,)
    }

    @Test
    fun `test city update`() {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.city = "Chicago"
        sut.updateCity(userId = "M1001-B00")
        sut.city = "Schererville"
        sut.getBillingInformation(userId = "M1001-B00")
        assertEquals( "Chicago", sut.city)
    }
    @Test
    fun `test zipCode update`()  {
        sut.addUser(
            fullname = "Mwai Banda",
            phone = "2190000000",
            password = "*******",
            email = "mwai.developer@gmail.com",
            createdOn = "08/09/2022",
            userId = "M1001-B00"
        )
        sut.zipCode = "1200"
        sut.updateZipCode(userId = "M1001-B00")
        sut.zipCode = "0000"
        sut.getBillingInformation(userId = "M1001-B00")
        assertEquals("1200", sut.zipCode)
    }
}