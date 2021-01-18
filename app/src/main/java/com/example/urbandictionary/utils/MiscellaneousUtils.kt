package com.example.urbandictionary.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()

    data class GenericError(
        val code: Int? = null,
        val error: Throwable? = null,
        val response: ErrorResponse? = null
    ) :
        NetworkResult<Nothing>()

    object NetworkError : NetworkResult<Nothing>()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            NetworkResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> NetworkResult.NetworkError
                is HttpException -> {

                    val code = throwable.code()
                    NetworkResult.GenericError(code, throwable)
                }
                else -> {
                    NetworkResult.GenericError(null, throwable)
                }
            }
        }
    }
}

data class ErrorResponse(
    val message: String?,
    val statusCode: Int?
)
