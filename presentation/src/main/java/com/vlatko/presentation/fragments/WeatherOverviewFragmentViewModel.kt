package com.vlatko.presentation.fragments

import com.vlatko.domain.IErrorParser
import com.vlatko.domain.interactors.GetCurrentWeatherInteractor
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.repositories.IApiRepo
import com.vlatko.presentation.base.BaseViewModel
import io.reactivex.Observable
import org.koin.standalone.inject

class WeatherOverviewFragmentViewModel : BaseViewModel() {

    private val apiRepo: IApiRepo by inject()
    private val errorParser: IErrorParser by inject()

    fun getWeatherForCurrentLocation() : Observable<CurrentWeather> = GetCurrentWeatherInteractor().execute()

    fun getWeatherByCityName(cityName: String) :Observable<CurrentWeather> = apiRepo.getCurrentWeatherByCityName(cityName)
    fun parseError(error : Throwable) : String? = errorParser.parse(error)

}