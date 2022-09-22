package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.SermonController
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.domain.repository.CacheRepository
import kotlinx.coroutines.launch

import com.mwaibanda.momentum.domain.usecase.sermon.GetSermonsUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SermonControllerImpl: SermonController, KoinComponent {
    private val getSermonsUseCase: GetSermonsUseCase by inject()
    private val scope = MainScope()

    override fun getSermon(pageNumber: Int, onCompletion: (Result<SermonResponse>) -> Unit) {
        scope.launch {
            getSermonsUseCase(pageNumber = pageNumber) {
                onCompletion(it)
            }
        }
    }
}