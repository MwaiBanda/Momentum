package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.data.ServiceResponse
import com.mwaibanda.momentum.domain.models.DayRequest
import com.mwaibanda.momentum.domain.models.Service
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.domain.models.VolunteerServiceRequest
import com.mwaibanda.momentum.utils.DataResponse

interface ServiceRepository {
    suspend fun fetchAllServices(): DataResponse<ServiceResponse>
    suspend fun fetchServiceByType(type: Tab.Type): DataResponse<Service>
    suspend fun postVolunteerService(request: VolunteerServiceRequest): DataResponse<VolunteerServiceRequest>
    suspend fun updateVolunteerServiceDay(request: DayRequest): DataResponse<DayRequest>
}