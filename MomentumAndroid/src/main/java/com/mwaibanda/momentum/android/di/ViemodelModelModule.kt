package com.mwaibanda.momentum.android.di

import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.event.EventViewModel
import com.mwaibanda.momentum.android.presentation.volunteer.meal.MealViewModel
import com.mwaibanda.momentum.android.presentation.message.MessageViewModel
import com.mwaibanda.momentum.android.presentation.offer.OfferViewModel
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentSummaryContentViewModel
import com.mwaibanda.momentum.android.presentation.offer.payment.PaymentViewModel
import com.mwaibanda.momentum.android.presentation.offer.profile.ProfileViewModel
import com.mwaibanda.momentum.android.presentation.sermon.SermonViewModel
import com.mwaibanda.momentum.android.presentation.offer.transaction.TransactionViewModel
import com.mwaibanda.momentum.android.presentation.volunteer.ServicesViewModel
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
    viewModel {
        MealViewModel(
            mealController = get(),
            notificationController = get()
        )
    }
    viewModel { AuthViewModel(authController = get(), localDefaultsController = get()) }
    viewModel { SermonViewModel(sermonController = get()) }
    viewModel { MessageViewModel(messageUseCases = get()) }
    viewModel { ServicesViewModel(servicesUseCase = get()) }
    viewModel { EventViewModel(eventController = get()) }
}