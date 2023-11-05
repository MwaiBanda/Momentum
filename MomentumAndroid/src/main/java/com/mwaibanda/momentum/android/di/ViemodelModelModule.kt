package com.mwaibanda.momentum.android.di

import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.meals.MealViewModel
import com.mwaibanda.momentum.android.presentation.messages.MessageViewModel
import com.mwaibanda.momentum.android.presentation.offer.OfferViewModel
import com.mwaibanda.momentum.android.presentation.payment.PaymentSummaryContentViewModel
import com.mwaibanda.momentum.android.presentation.payment.PaymentViewModel
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel
import com.mwaibanda.momentum.android.presentation.sermon.SermonViewModel
import com.mwaibanda.momentum.android.presentation.transaction.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PaymentViewModel(get()) }
    viewModel { TransactionViewModel(get()) }
    viewModel { OfferViewModel() }
    viewModel { PaymentSummaryContentViewModel() }
    viewModel {
        ProfileViewModel(
            userController = get(),
            billingAddressController = get(),
            localDefaultsController = get()
        )
    }
    viewModel { MealViewModel(mealController = get()) }
    viewModel { AuthViewModel(authController = get(), localDefaultsController = get()) }
    viewModel { SermonViewModel(sermonController = get()) }
    viewModel { MessageViewModel(messageController = get()) }
}