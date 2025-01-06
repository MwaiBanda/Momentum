package com.mwaibanda.momentum.domain.usecase.service

import com.mwaibanda.momentum.domain.models.VolunteerServiceRequest
import com.mwaibanda.momentum.domain.repository.ServiceRepository
import com.mwaibanda.momentum.utils.CommonFlow
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlin.native.ObjCName

class PostVolunteerServiceUseCase(
    private val serviceRepository: ServiceRepository
) {
    @ObjCName("execute")
    operator fun invoke(request: VolunteerServiceRequest): CommonFlow<Result<VolunteerServiceRequest>> = flow {
        emit(Result.Loading())
        when(val res = serviceRepository.postVolunteerService(request)) {
            is DataResponse.Failure -> emit(Result.Error(message = res.message ?: "[PostVolunteerServiceUseCase]: Unknown Error"))
            is DataResponse.Success -> emit(Result.Data(data = res.data))
        }
    }.asCommonFlow()
}