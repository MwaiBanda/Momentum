package com.mwaibanda.momentum.domain.usecase.service

import com.mwaibanda.momentum.data.ServiceResponse
import com.mwaibanda.momentum.domain.repository.ServiceRepository
import com.mwaibanda.momentum.utils.CommonFlow
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlin.native.ObjCName

class GetAllServicesUseCase(
    private val serviceRepository: ServiceRepository
) {
    @ObjCName("execute")
    operator fun invoke(): CommonFlow<Result<ServiceResponse>> = flow {
        emit(Result.Loading())
        when(val res = serviceRepository.fetchAllServices()) {
            is DataResponse.Failure -> emit(Result.Error(message = res.message ?: "[GetAllServicesUseCase]: Unknown Error"))
            is DataResponse.Success -> emit(
                    Result.Data(
                        data = res.data ?: emptyList()
                    )
                )

        }
    }.asCommonFlow()
}