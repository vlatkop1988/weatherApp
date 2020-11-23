package com.vlatko.presentation.fragments

import com.vlatko.domain.IErrorParser
import com.vlatko.domain.interactors.GetCurrentWeatherInteractor
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.repositories.IApiRepo
import com.vlatko.domain.repositories.ILocalStorageRepo
import com.vlatko.presentation.base.BaseViewModel
import io.reactivex.Observable
import org.koin.standalone.inject
import java.util.*

class WeatherOverviewFragmentViewModel : BaseViewModel() {

    private val apiRepo: IApiRepo by inject()
    private val localStorageRepo: ILocalStorageRepo by inject()
    private val errorParser: IErrorParser by inject()
    private val lang = Locale.getDefault().language

    fun getWeatherForCurrentLocation(): Observable<CurrentWeather> =
        GetCurrentWeatherInteractor(localStorageRepo.getUnit(), lang).execute()

    fun getWeatherByCityName(cityName: String): Observable<CurrentWeather> =
        apiRepo.getCurrentWeatherByCityName(cityName, localStorageRepo.getUnit(), lang)

    fun parseError(error: Throwable): String? = errorParser.parse(error)

}