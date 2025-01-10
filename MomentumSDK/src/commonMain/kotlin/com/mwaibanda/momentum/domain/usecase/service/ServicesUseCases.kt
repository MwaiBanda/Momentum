package com.mwaibanda.momentum.domain.usecase.service

data class ServicesUseCases(
    val create: PostVolunteerServiceUseCase,
    val updateDay: PostVolunteerServiceDayUseCase,
    val readByType: GetServiceByTypeUseCase,
    val read: GetAllServicesUseCase
)
