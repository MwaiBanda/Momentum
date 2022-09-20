package com.mwaibanda.momentum.domain.usecase.sermon

import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.repository.SermonRepository
import com.mwaibanda.momentum.utils.Result

class GetSermonsUseCase(
    private val sermonRepository: SermonRepository
) {
    suspend operator fun invoke(pageNumber: Int, onCompletion: (Result<List<Sermon>>) -> Unit) {
        onCompletion(sermonRepository.getSermon(pageNumber = pageNumber))
    }
}