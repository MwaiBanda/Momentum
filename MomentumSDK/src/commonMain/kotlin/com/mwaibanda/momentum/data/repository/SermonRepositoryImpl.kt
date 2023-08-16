package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.sermonDTO.SermonContainerDTO
import com.mwaibanda.momentum.data.sermonDTO.toSermon
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.domain.repository.SermonRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetAllItemsUseCase
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class SermonRepositoryImpl(
    private val httpClient: HttpClient,
    private val getItemUseCase: GetItemUseCase<SermonResponse>,
    private val setItemUseCase: SetItemUseCase<SermonResponse>,
    private val getAllItemsUseCase: GetAllItemsUseCase<SermonResponse>
) : SermonRepository, MomentumBase() {

    override suspend fun getSermons(pageNumber: Int): Result<SermonResponse> {
        val cachedResponse = getItemUseCase(key = "page$pageNumber")?.sermons.orEmpty()
        if (cachedResponse.isNotEmpty()) {
            val allCachedSermonResponses = getAllItemsUseCase()
            return Result.Success(SermonResponse(
                pageNumber = allCachedSermonResponses.maxBy { it.pageNumber }.pageNumber,
                sermons = allCachedSermonResponses.flatMap { it.sermons },
                canLoadMoreSermons = allCachedSermonResponses.maxBy { it.pageNumber }.canLoadMoreSermons
            ))
        }


        try {
            val sermonDTO: SermonContainerDTO = httpClient.get {
                momentumAPI(
                    SERMONS_ENDPOINT,
                    params = hashMapOf(
                        "page" to "$pageNumber"
                    )
                )
            }.body()
            val sermons = sermonDTO.sermonDTOS.map { it.toSermon() }
            setItemUseCase(
                key = "page$pageNumber",
                value = SermonResponse(
                    pageNumber = pageNumber,
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