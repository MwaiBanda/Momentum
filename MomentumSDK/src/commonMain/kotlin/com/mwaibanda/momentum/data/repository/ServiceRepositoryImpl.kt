package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase.Companion.SERVICES_ENDPOINT
import com.mwaibanda.momentum.data.MomentumBase.Companion.momentumAPI
import com.mwaibanda.momentum.data.ServiceResponse
import com.mwaibanda.momentum.domain.models.DayRequest
import com.mwaibanda.momentum.domain.models.Service
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.domain.models.VolunteerServiceRequest
import com.mwaibanda.momentum.domain.models.value
import com.mwaibanda.momentum.domain.repository.ServiceRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.MultiplatformConstants.SERVICES_KEY
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

class ServiceRepositoryImpl(
    private val httpClient: HttpClient,
    private val getItemUseCase: GetItemUseCase<ServiceResponse>,
    private val setItemUseCase: SetItemUseCase<ServiceResponse>,
): ServiceRepository {
    override suspend fun fetchAllServices(): DataResponse<ServiceResponse> {
        val cacheMessages = getItemUseCase(SERVICES_KEY).orEmpty()
        if (cacheMessages.isNotEmpty()) {
            return DataResponse.Success(cacheMessages)
        }
        try {
            val messages = httpClient.get {
                momentumAPI(SERVICES_ENDPOINT)
            }.body<ServiceResponse>()
            setItemUseCase(SERVICES_KEY, messages)
        } catch (e: Exception) {
            return DataResponse.Failure(e.message.toString())
        }
        val newlyCachedMessages = getItemUseCase(SERVICES_KEY).orEmpty()
        return DataResponse.Success(newlyCachedMessages)
    }

    override suspend fun fetchServiceByType(type: Tab.Type): DataResponse<Service> {
        try {
            val service = httpClient.get {
                momentumAPI("$SERVICES_ENDPOINT/${type.value}")
            }.body<Service>()
            return DataResponse.Success(service)
        } catch (e: Exception) {
            return DataResponse.Failure(e.message.toString())
        }
    }

    override suspend fun postVolunteerService(request: VolunteerServiceRequest): DataResponse<VolunteerServiceRequest> {
        return try {
            val data = httpClient.post {
                momentumAPI(SERVICES_ENDPOINT, request)
            }.body<VolunteerServiceRequest>()
            DataResponse.Success(data)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }

    override suspend fun updateVolunteerServiceDay(request: DayRequest): DataResponse<DayRequest> {
        return try {
            val data = httpClient.put {
                momentumAPI("$SERVICES_ENDPOINT/day", request)
            }.body<DayRequest>()
            DataResponse.Success(data)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }
}