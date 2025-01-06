package com.mwaibanda.momentum.android.presentation.volunteer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwaibanda.momentum.domain.models.Service
import com.mwaibanda.momentum.domain.models.Tab
import com.mwaibanda.momentum.domain.models.VolunteerServiceRequest
import com.mwaibanda.momentum.domain.usecase.service.ServicesUseCases
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val servicesUseCase: ServicesUseCases
): ViewModel() {
    private val _services = MutableStateFlow(emptyList<Tab>())
    val services = _services.asStateFlow()

    private val _service: MutableStateFlow<Service?> = MutableStateFlow(null)
    val service = _service.asStateFlow()

    private val _refreshCount = MutableStateFlow(0)
    val refreshCount = _refreshCount.asStateFlow()

    fun getServices() {
        viewModelScope.launch {
            servicesUseCase.read().collectLatest {
                when(it) {
                    is Result.Data -> {
                        _services.value = (it.data ?: emptyList())
                        Log.e("GET[Data]", "Services")
                    }
                    is Result.Error -> Log.e("ServicesViewModel[getServices]", it.message ?: "")

                    is Result.Loading -> Log.e("GET[Loading]", "Services")
                }
            }
        }
    }

    fun getServiceByType(type: Tab.Type) {
        viewModelScope.launch {
            servicesUseCase.readByType(type).collectLatest {
                when(it) {
                    is Result.Data -> {
                        _service.value = it.data
                        Log.e("GET[Data]", "Service")
                    }
                    is Result.Error -> Log.e("ServicesViewModel[getServiceByType]", it.message ?: "")

                    is Result.Loading -> Log.e("GET[Loading]", "Service")
                }
            }
        }
    }

    fun postVolunteerService(request: VolunteerServiceRequest) {
        viewModelScope.launch {
            servicesUseCase.create(request).collectLatest {
                when(it) {
                    is Result.Data -> {
                        _refreshCount.value++
                        Log.e("GET[Data]", "Service")
                    }
                    is Result.Error -> Log.e("ServicesViewModel[getServiceByType]", it.message ?: "")

                    is Result.Loading -> Log.e("GET[Loading]", "Service")
                }
            }
        }
    }
}