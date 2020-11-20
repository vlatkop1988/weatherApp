package com.vlatko.presentation.base

import com.vlatko.domain.ErrorParser
import com.vlatko.domain.interactors.GetCurrentWeatherInteractor
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.repositories.IApiRepo
import io.reactivex.Observable
import org.koin.standalone.inject

class WeatherOverviewFragmentViewModel : BaseViewModel() {

    private val apiRepo: IApiRepo by inject()
    private val errorParser: ErrorParser by inject()

    fun getCurrentWeather(cityName: String?): Observable<CurrentWeather> =
        apiRepo.getCurrentWeatherByCityName(cityName)

    fun getCurrentWeather() : Observable<CurrentWeather> = GetCurrentWeatherInteractor().execute()

    fun parseError(error : Throwable) : String? = errorParser.parse(error)

}