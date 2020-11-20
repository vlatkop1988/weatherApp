package com.vlatko.data.network

import com.google.gson.Gson
import com.vlatko.data.network.interceptors.LoggingInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitConfig {

    private var retrofit: Retrofit? = null
    private var customGson: Gson? = null

    private fun init(baseUrl: String) {
        retrofit = createRetrofit(baseUrl)
    }

    private fun createRetrofit(baseUrl: String) = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(getConverterFactory())
        .client(createOkHttpClient())
        .build()

    private fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun get(): Retrofit {
        return retrofit ?: throw RuntimeException("Invoke init(baseUrl) first.")
    }

    private fun getConverterFactory(): GsonConverterFactory {
        val gson = customGson
        return if (gson == null) {
            GsonConverterFactory.create()
        } else {
            GsonConverterFactory.create(gson)
        }
    }

    private fun setCustomGson(gson: Gson) {
        customGson = gson
    }

    companion object {

        private val instance = RetrofitConfig()
        val init = instance::init
        val setCustomGson = instance::setCustomGson
        internal val get = instance::get
    }
}