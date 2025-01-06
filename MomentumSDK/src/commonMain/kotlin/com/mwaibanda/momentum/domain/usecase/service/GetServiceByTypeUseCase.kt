package com.mwaibanda.momentum.domain.usecase.service

import com.mwaibanda.momentum.domain.models.Service
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.domain.repository.ServiceRepository
import com.mwaibanda.momentum.utils.CommonFlow
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlin.native.ObjCName

class GetServiceByTypeUseCase(
    private val serviceRepository: ServiceRepository
) {
    @ObjCName("execute")
    operator fun invoke(type: Tab.Type): CommonFlow<Result<Service>> = flow {
        emit(Result.Loading())
        when(val res = serviceRepository.fetchServiceByType(type)) {
            is DataResponse.Failure -> emit(Result.Error(message = res.message ?: "[GetServiceByTypeUseCase]: Unknown Error"))
            is DataResponse.Success -> emit(Result.Data(data = res.data))
        }
    }.asCommonFlow()
}