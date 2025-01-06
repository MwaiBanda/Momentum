package com.mwaibanda.momentum.domain.usecase.service

data class ServicesUseCases(
    val create: PostVolunteerServiceUseCase,
    val readByType: GetServiceByTypeUseCase,
    val read: GetAllServicesUseCase
)
