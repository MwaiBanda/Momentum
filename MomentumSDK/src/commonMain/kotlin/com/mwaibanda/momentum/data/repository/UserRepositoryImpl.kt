package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.DataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

class UserRepositoryImpl(
    private val httpClient: HttpClient
): MomentumBase(), UserRepository {

    override suspend fun postUser(user: User) {
        try {
            httpClient.post {
                momentumAPI(USERS_ENDPOINT, user)
            }
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }

    override suspend fun fetchUser(userId: String): DataResponse<User> {
        return try {
            val response: User = httpClient.get {
                momentumAPI("$USERS_ENDPOINT/$userId")
            }.body()
            DataResponse.Success(response)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }

    override suspend fun updateUser(user: User): DataResponse<User> {
        return try {
            val response: User = httpClient.put {
                momentumAPI(USERS_ENDPOINT, user)
            }.body()
            return DataResponse.Success(response)
        } catch (e: Exception) {
            DataResponse.Failure(e.message ?: "Unknown Error")
        }
    }



    override suspend fun deleteUser(userId: String) {
        try {
            httpClient.delete {
                momentumAPI("$USERS_ENDPOINT/$userId")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}