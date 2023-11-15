package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.domain.repository.NotificationRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class NotificationRepositoryImpl(
    private val httpClient: HttpClient
): MomentumBase(), NotificationRepository {
    override suspend fun postNotification(notification: Notification): Result<Notification> {
        return try {
            val response: Notification = httpClient.post {
                momentumAPI(NOTIFICATIONS_ENDPOINT)
                contentType(ContentType.Application.Json)
                setBody(notification)
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }
}