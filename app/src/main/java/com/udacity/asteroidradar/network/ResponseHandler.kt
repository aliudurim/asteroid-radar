package com.udacity.asteroidradar.network

import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(
                getErrorMessage(
                    e.code(),
                    e.response()?.errorBody()?.string()
                ), null
            )
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int, errorResponseBody: String? = null): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> {
                if (!errorResponseBody.isNullOrEmpty()) {
                    val json = JSONObject(errorResponseBody)
                    val msg = json.optString("msg")
                    val message = json.optString("message")
                    return if (message.isNullOrEmpty())
                        if (msg.isNullOrEmpty())
                            "An error has occurred please try again later!"
                        else
                            msg
                    else
                        message
                } else {
                    "An error has occurred please try again later!"
                }
            }
        }
    }
}