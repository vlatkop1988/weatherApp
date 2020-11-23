package com.vlatko.domain.interactors

import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.repositories.IApiRepo
import io.reactivex.Observable
import org.koin.standalone.inject

class GetCurrentWeatherByGeoLocationInteractor(
    private val lat: String?,
    private val lon: String?,
    private val unit: String,
    private val lang: String
) :
    Interactor<CurrentWeather> {
    private val apiRepo: IApiRepo by inject()
    override fun execute(): Observable<CurrentWeather> =
        apiRepo.getCurrentWeatherByGeoLocation(lat, lon, unit, lang)
}