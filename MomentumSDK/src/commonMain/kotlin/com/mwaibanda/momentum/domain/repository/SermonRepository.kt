package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.utils.Result

interface SermonRepository {
    suspend fun getSermons(pageNumber: Int): Result<SermonResponse>
}