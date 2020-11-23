package com.vlatko.data.network

import com.vlatko.data.network.mappers.toDomain
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.repositories.IApiRepo
import io.reactivex.Observable
import org.koin.standalone.KoinComponent

class ApiRepoImpl : IApiRepo, KoinComponent {
    private val appId = "de1811b1afe43f13308c51d369048d0e"
    private val weatherService = ServiceFactory.instance.getService(WeatherService::class.java)

    override fun getCurrentWeatherByCityName(
        cityName: String?,
        unit: String,
        lang: String
    ): Observable<CurrentWeather> =
        weatherService.getCurrentWeatherByCityName(cityName, appId, unit, lang)
            .map { it.toDomain() }

    override fun getCurrentWeatherByGeoLocation(
        lat: String?,
        lon: String?,
        unit: String,
        lang: String
    ): Observable<CurrentWeather> =
        weatherService.getCurrentWeatherByGeoLocation(lat, lon, appId, unit, lang)
            .map { it.toDomain() }

}