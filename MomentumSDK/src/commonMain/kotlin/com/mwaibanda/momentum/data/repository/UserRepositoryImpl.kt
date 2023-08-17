package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.MomentumBase.Companion.momentumAPI
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.domain.repository.UserRepository
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.mwaibanda.momentum.utils.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

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

    override suspend fun getUser(userId: String): Result<User> {
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