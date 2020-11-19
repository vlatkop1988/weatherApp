package com.vlatko.data.network.error

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vlatko.data.network.response.ErrorResponse
import retrofit2.HttpException

class APIError(error: HttpException? = null, reason: Reason) : Throwable() {

    val errorResponse: ErrorResponse = Gson().fromJson(
        error?.response()?.errorBody()?.string(),
        object : TypeToken<ErrorResponse>() {}.type
    )

    val  reason = reason
}

enum class Reason { API, NETWORK_DOWN, UNKNOWN, SERVER }