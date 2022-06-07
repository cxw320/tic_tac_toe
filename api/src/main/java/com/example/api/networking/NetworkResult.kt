package com.example.api.networking

import java.io.IOException
import java.net.HttpURLConnection

sealed class NetworkResult<out T : Any> {

    data class Success<T : Any>(val body: T) : NetworkResult<T>()

    data class Error(val error: Exception, val code: Int) : NetworkResult<Nothing>() {
        val isAuthError: Boolean
            get() = code in listOf(
                HttpURLConnection.HTTP_UNAUTHORIZED,
                HttpURLConnection.HTTP_FORBIDDEN
            )

        val isServerError: Boolean
            get() = code in HttpURLConnection.HTTP_INTERNAL_ERROR..HttpURLConnection.HTTP_GATEWAY_TIMEOUT

        val isUndefined: Boolean
            get() = code == 422

        val isConflict: Boolean
            get() = code == HttpURLConnection.HTTP_CONFLICT
    }

    data class Failure(val throwable: Throwable) : NetworkResult<Nothing>() {
        val isNetworkFailure: Boolean
            get() = throwable is IOException
    }
}