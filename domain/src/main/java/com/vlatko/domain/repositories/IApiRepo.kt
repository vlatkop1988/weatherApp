package com.vlatko.domain.repositories

import com.vlatko.domain.models.CurrentWeather
import io.reactivex.Observable

interface IApiRepo {
    fun getCurrentWeatherByCityName(
        cityName: String?,
        unit: String,
        lang: String
    ): Observable<CurrentWeather>

    fun getCurrentWeatherByGeoLocation(
        lat: String?,
        lon: String?,
        unit: String,
        lang: String
    ): Observable<CurrentWeather>
}