package com.seven.assignment.data.repository

import com.google.gson.Gson
import com.seven.assignment.data.Result
import retrofit2.Response
import java.net.ConnectException

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource(val gson: Gson) {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null)
                    Result.success(body)
                else
                    Result.success()
            }
            return error(response.code(), response.message())
        } catch (e: Exception) {
            return if (e is ConnectException) {
                error(NO_INTERNET)
            } else {
                error(e.message ?: e.toString())
            }
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error(message)
    }

    private fun <T> error(code: Int, message: String): Result<T> {
        return when (code) {
            400 -> Result.error(ERROR_400)
            401 -> Result.error(ERROR_401)
            404 -> Result.error(ERROR_404)
            405 -> Result.error(ERROR_405)
            408 -> Result.error(ERROR_408)
            508 -> Result.error(ERROR_500)
            else -> Result.error(message)
        }
    }

    companion object {
        const val NO_INTERNET = "Please check your internet connection"
        const val ERROR_500 = "Something is wrong in the server, please try later"
        const val ERROR_400 = "Something is wrong, please try later"
        const val ERROR_408 = "Timed out. Please check your internet."
        const val ERROR_401 = "Your session might have expired. Please login."
        const val ERROR_404 = "The information is not found."
        const val ERROR_405 = "This operation is not allowed."
    }

}