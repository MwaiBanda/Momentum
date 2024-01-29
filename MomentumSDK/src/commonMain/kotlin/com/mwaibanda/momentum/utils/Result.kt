package com.mwaibanda.momentum.utils

sealed class Result<T>(
    val data: T? = null,
    val message:  String?  = null
) {
    class Success<T>(data: T): Result<T>(data)
    class Failure<T>(message: String, data:  T? = null): Result<T>(data, message)
}


