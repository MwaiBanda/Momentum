package com.mwaibanda.momentum.android.di

import com.mwaibanda.momentum.android.presentation.offer.OfferViewModel
import com.mwaibanda.momentum.android.presentation.payment.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PaymentViewModel() }
    viewModel { OfferViewModel() }
}