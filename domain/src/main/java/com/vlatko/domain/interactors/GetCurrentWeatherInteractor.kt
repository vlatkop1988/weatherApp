package com.vlatko.domain.interactors

import com.vlatko.domain.models.CurrentWeather
import io.reactivex.Observable

class GetCurrentWeatherInteractor : Interactor<CurrentWeather> {
    override fun execute(): Observable<CurrentWeather> {
        return GetCurrentLocationInteractor().execute().concatMap {
            GetCurrentWeatherByGeoLocationInteractor(
                it.latitude.toString(),
                it.longitude.toString()
            ).execute()
        }
    }
}