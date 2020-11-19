package com.vlatko.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

const val X_AUTH_TOKEN = "x-auth-token"
const val X_LOCALE = "x-locale"

internal class HeaderInterceptor : Interceptor {

    private val headers = HashMap<String, String>()

    internal fun setHeader(key: String, value: String?) {
        if (value.isNullOrBlank()) {
            headers.remove(key)
        } else {
            headers[key] = value
        }
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val chainRequestBuilder = chain.request().newBuilder()
        val iterator = headers.iterator()
        var header: Map.Entry<String, String>?
        while (iterator.hasNext()) {
            header = iterator.next()
            chainRequestBuilder.addHeader(header.key, header.value)
        }
        return chain.proceed(chainRequestBuilder.build())
    }
}