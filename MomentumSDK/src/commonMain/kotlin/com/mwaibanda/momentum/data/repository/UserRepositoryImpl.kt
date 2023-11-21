package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserRepositoryImpl(
    private val httpClient: HttpClient
): MomentumBase(), UserRepository {

    override suspend fun postUser(user: User) {
        httpClient.post {
            momentumAPI(USERS_ENDPOINT)
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    override suspend fun fetchUser(userId: String): Result<User> {
        return try {
            val response: User = httpClient.get {
                momentumAPI("$USERS_ENDPOINT/$userId")
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun updateUser(user: User): User {
        val response: User = httpClient.put {
            momentumAPI(USERS_ENDPOINT)
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
        return response
    }



    override suspend fun deleteUser(userId: String) {
        httpClient.delete {
            momentumAPI("$USERS_ENDPOINT/$userId")
        }
    }
}