package com.mwaibanda.momentum.utils

sealed class DataResponse<T>(
    val data: T? = null,
    val message:  String?  = null
) {
    class Success<T>(data: T): DataResponse<T>(data)
    class Failure<T>(message: String, data:  T? = null): DataResponse<T>(data, message)
}


