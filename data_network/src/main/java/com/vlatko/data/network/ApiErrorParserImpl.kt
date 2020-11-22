package com.vlatko.data.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vlatko.data.network.response.WeatherErrorResponse
import com.vlatko.domain.IErrorParser
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

private const val TAG = "ErrorHandler"

class ApiErrorParserImpl : IErrorParser {

    override fun parse(error: Throwable): String? {
        return when (error) {
            is HttpException -> {
                val errorResponse: WeatherErrorResponse = Gson().fromJson(
                    error.response()?.errorBody()?.string(),
                    object : TypeToken<WeatherErrorResponse>() {}.type
                )
                errorResponse.message
            }
            is UnknownHostException -> {
                Log.e(TAG, "UnknownHostError ${error.message}")
                "Unknown host" //TODO localize
            }
            is IOException -> {
                Log.e(TAG, "IOError ${error.message}")
                "IOException" //TODO localize
            }
            else -> {
                Log.d(TAG, "UnknownError ${error.message}")
                "UnknownError" //TODO localize
            }
        }
    }
}