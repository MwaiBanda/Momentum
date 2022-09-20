package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.utils.Result

interface SermonRepository {
    suspend fun getSermon(pageNumber: Int): Result<List<Sermon>>
}