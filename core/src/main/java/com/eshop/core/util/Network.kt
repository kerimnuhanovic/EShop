package com.eshop.core.util

import com.eshop.core.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun getMessageIdForServerError(statusCode: Int): Int {
    return when (statusCode) {
        in 400 until 500 -> R.string.api_error_4xx
        in 500 until 600 -> R.string.api_error_5xx
        else -> R.string.api_error_unknown
    }
}

private fun getMessageIdForFailedLogin(statusCode: Int): Int {
    return when (statusCode) {
        in 400 until 500 -> R.string.invalid_credentials
        in 500 until 600 -> R.string.api_error_5xx
        else -> R.string.api_error_unknown
    }
}

private fun handleIoException(e: IOException): Result.Failure {
    return when (e) {
        is SocketTimeoutException -> Result.Failure(errorMessageId = R.string.api_error_timeout)
        else -> Result.Failure(errorMessageId = R.string.api_error_no_connection)
    }
}

fun handleLoginApiFailure(ex: Exception): Result.Failure {
    return when (ex) {
        is HttpException -> Result.Failure(exception = ex, errorCode = ex.code(), errorMessageId = getMessageIdForFailedLogin(ex.code()))
        is IOException -> handleIoException(ex)
        else -> Result.Failure(exception = ex, errorMessageId = R.string.api_error_unknown)
    }
}

fun handleSignupApiFailure(ex: Exception): Result.Failure {
    return when (ex) {
        is HttpException -> Result.Failure(exception = ex, errorCode = ex.code(), errorMessageId = getMessageIdForFailedSignup(ex.code()))
        is IOException -> handleIoException(ex)
        else -> Result.Failure(exception = ex, errorMessageId = R.string.api_error_unknown)
    }
}

private fun getMessageIdForFailedSignup(statusCode: Int): Int {
    return when (statusCode) {
        in 400 until 500 -> R.string.user_already_exists
        in 500 until 600 -> R.string.api_error_5xx
        else -> R.string.api_error_unknown
    }
}
