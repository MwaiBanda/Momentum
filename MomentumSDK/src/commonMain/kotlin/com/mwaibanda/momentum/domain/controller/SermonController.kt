package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.utils.Result

interface SermonController {
    fun getSermon(pageNumber: Int, onCompletion: (Result<SermonResponse>) -> Unit)
}