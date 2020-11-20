package com.vlatko.domain.repositories

import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.models.base.Result
import io.reactivex.Observable
import io.reactivex.Single

interface IApiRepo {
    fun getCurrentWeatherByCityName(cityName : String?) : Observable<CurrentWeather>
}