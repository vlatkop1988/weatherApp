package com.vlatko.data.network

import com.vlatko.data.network.response.ResponseCurrentWeather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getCurrentWeatherByCityName(
        @Query("q") cityName: String?,
        @Query("appid") appId: String?
    ): Observable<ResponseCurrentWeather>
}