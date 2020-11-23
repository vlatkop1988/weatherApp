package com.vlatko.domain.interactors

import com.vlatko.domain.models.CurrentWeather
import io.reactivex.Observable

class GetCurrentWeatherInteractor(private val unit: String, private val lang: String) :
    Interactor<CurrentWeather> {
    override fun execute(): Observable<CurrentWeather> {
        return GetCurrentLocationInteractor().execute().concatMap {
            GetCurrentWeatherByGeoLocationInteractor(
                it.latitude.toString(),
                it.longitude.toString(),
                unit,
                lang
            ).execute()
        }
    }
}