package com.mwaibanda.momentum.utils

sealed class State<T>(
    val message: String? = null,
    val data: T? = null,
) {
    class Data<T>( data: T?, message: String? = null): State<T>(message, data)
    class Loading<T>(message: String? = null, data:T? = null): State<T>(message, data)
    class Error<T>(message: String?, data:  T? = null): State<T>(message, data)
}