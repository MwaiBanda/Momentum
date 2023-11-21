package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.utils.Result

interface SermonRepository {
    suspend fun fetchSermons(pageNumber: Int): Result<SermonResponse>
}