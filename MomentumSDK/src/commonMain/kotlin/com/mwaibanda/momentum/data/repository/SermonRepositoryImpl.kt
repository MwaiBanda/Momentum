package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.MomentumBase.Companion.momentumSermons
import com.mwaibanda.momentum.data.sermonDTO.SermonContainerDTO
import com.mwaibanda.momentum.data.sermonDTO.toSermon
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.domain.repository.SermonRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class SermonRepositoryImpl(
    private val httpClient: HttpClient
): SermonRepository, MomentumBase() {

    override suspend fun getSermon(pageNumber: Int): Result<List<Sermon>> {
        return try {
            val response: List<Sermon> = httpClient.get {
                momentumSermons(
                    SERMONS_ENDPOINT,
                    params = hashMapOf(
                        "howmany" to "12",
                        "page" to "$pageNumber"
                    )
                )
            }.body<SermonContainerDTO>().sermonDTOS.map { it.toSermon() }
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }
}