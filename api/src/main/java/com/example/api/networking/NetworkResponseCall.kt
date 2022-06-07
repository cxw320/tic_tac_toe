package com.example.api.networking

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

internal class NetworkResponseCall<T : Any>(
    private val delegate: Call<T>
) : Call<NetworkResult<T>> {
    override fun enqueue(callback: Callback<NetworkResult<T>>) =
        delegate.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResponse = NetworkResult.Failure(t)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

            /**
             * Handle our response.  We have four cases currently
             * - Success with body: just create a NetworkResult.Success with the body
             * - Success with no body and a 204 code: this is expected, so we need to send Unit as the type in NetworkResult.Success; the onus is on the API to implement this
             * with a return type of Unit
             * - Success with no body a non-204 code: we don't know what went wrong here, so we propagate it as an error with an EmptyResponseException
             * - Error: pass to handleError to determine the cause and send it as a NetworkResult.Error
             */
            @Suppress("UNCHECKED_CAST")
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body: T? = response.body()
                if (response.isSuccessful) {
                    when {
                        body != null -> callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResult.Success(body))
                        )
                        response.code() == HttpURLConnection.HTTP_NO_CONTENT -> callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResult.Success(Unit as T))
                        )
                        else -> callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResult.Error(Exception(), response.code()))
                        )
                    }
                } else {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(
                            NetworkResult.Error(
                                Exception(response.message()),
                                response.code()
                            )
                        )
                    )
                }
            }
        })

    /*
    The rest of these methods are delegating to the...delegate (pretty much)
     */

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<NetworkResult<T>> {
        return NetworkResponseCall(
            delegate.clone()
        )
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel(): Unit = delegate.cancel()

    /*
    Not handling cases of synchronous execution.
    If there are cases where a synchronous call is needed,
    just use a non-suspending function
     */
    override fun execute(): Response<NetworkResult<T>> {
        try {
            val apiResponse = delegate.execute()
            if (apiResponse.isSuccessful) {
                val body = apiResponse.body()
                return if (body != null) {
                    Response.success(NetworkResult.Success(body))
                } else {
                    Response.success(
                        NetworkResult.Error(
                            Exception(), apiResponse.code()
                        )
                    )
                }
            } else {
                return Response.success(
                    NetworkResult.Error(
                        Exception(apiResponse.message()),
                        apiResponse.code()
                    )
                )
            }
        } catch (t: Throwable) {
            return Response.success(NetworkResult.Failure(t))
        }
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}