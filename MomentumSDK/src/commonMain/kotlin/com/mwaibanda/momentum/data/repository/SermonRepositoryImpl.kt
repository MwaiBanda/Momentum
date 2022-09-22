package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.MomentumBase.Companion.momentumSermons
import com.mwaibanda.momentum.data.sermonDTO.SermonContainerDTO
import com.mwaibanda.momentum.data.sermonDTO.SermonDTO
import com.mwaibanda.momentum.data.sermonDTO.toSermon
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.domain.repository.CacheRepository
import com.mwaibanda.momentum.domain.repository.SermonRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class SermonRepositoryImpl(
    private val httpClient: HttpClient,
    private val getItemUseCase: GetItemUseCase<SermonResponse>,
    private val setItemUseCase: SetItemUseCase<SermonResponse>
) : SermonRepository, MomentumBase() {

    override suspend fun getSermon(pageNumber: Int): Result<SermonResponse> {
        val response = getItemUseCase(key = "page$pageNumber")
        if (response != null) return Result.Success(response)

        try {
            val sermonDTO: SermonContainerDTO = httpClient.get {
                momentumSermons(
                    SERMONS_ENDPOINT,
                    params = hashMapOf(
                        "howmany" to "12",
                        "page" to "$pageNumber"
                    )
                )
            }.body()
            val sermons = sermonDTO.sermonDTOS.map { it.toSermon() }
            setItemUseCase(
                key = "page$pageNumber",
                value = SermonResponse(
                    sermons = sermons,
                    canLoadMoreSermons = sermonDTO.next_page_url.isNullOrBlank().not()
                )
            )
        } catch (e: Exception) {
            return Result.Failure(e.message.toString())
        }
        val newlyCacheSermons = getItemUseCase(key = "page$pageNumber")
        return Result.Success(newlyCacheSermons!!)
    }
}