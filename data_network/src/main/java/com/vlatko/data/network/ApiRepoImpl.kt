package com.vlatko.data.network

import com.vlatko.data.network.mappers.toDomain
import com.vlatko.data.network.response.ResponseCurrentWeather
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.models.base.Result
import com.vlatko.domain.repositories.IApiRepo
import io.reactivex.Observable
import org.koin.standalone.KoinComponent

class ApiRepoImpl : IApiRepo, KoinComponent {
    private val appId = "de1811b1afe43f13308c51d369048d0e"
    private val weatherService = ServiceFactory.instance.getService(WeatherService::class.java)

    override fun getCurrentWeatherByCityName(cityName: String?): Observable<Result<CurrentWeather, String>> =
        Response<ResponseCurrentWeather, Result<CurrentWeather, String>>(
            weatherService.getCurrentWeatherByCityName(cityName, appId),
            { Result(response = it.toDomain()) },
            { Result(error = it.errorResponse.message) }
        ).toObservable()

}