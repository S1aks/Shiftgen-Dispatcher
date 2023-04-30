package com.s1aks.shiftgen_dispatcher.data

sealed class ResponseState<out T> {
    object Idle : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
    data class Error(val error: Throwable) : ResponseState<Nothing>()
    data class Success<T>(val item: T) : ResponseState<T>()
}
