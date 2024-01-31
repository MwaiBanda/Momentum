package com.mwaibanda.momentum.utils


sealed class Result<T>(
    val data: T? = null,
    val message: String? = null,
    val status: ResultStatus
) {
    class Data<T>(
        data: T?,
        message: String? = null,
        status: ResultStatus = ResultStatus.DATA
    ): Result<T>(data, message, status)
    class Loading<T>(
        message: String? = null,
        data:T? = null,
        status: ResultStatus = ResultStatus.LOADING
    ): Result<T>(data, message, status)
    class Error<T>(
        message: String?,
        data:  T? = null,
        status: ResultStatus = ResultStatus.ERROR
    ): Result<T>(data, message, status)
}