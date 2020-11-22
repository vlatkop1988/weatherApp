package com.vlatko.presentation.fragments

import com.vlatko.domain.IErrorParser
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.repositories.IApiRepo
import com.vlatko.domain.repositories.ILocalStorageRepo
import com.vlatko.domain.repositories.ILocationRepo
import com.vlatko.presentation.base.BaseViewModel
import io.reactivex.Observable
import org.koin.standalone.inject

class SearchFragmentViewModel : BaseViewModel() {
    private val apiRepo: IApiRepo by inject()
    private val localStorageRepo: ILocalStorageRepo by inject()
    private val errorParser: IErrorParser by inject()

    fun searchForCity(cityName: String?): Observable<CurrentWeather> =
        apiRepo.getCurrentWeatherByCityName(cityName)

    fun addFavoriteCity(cityName: String) = localStorageRepo.addFavoriteCity(cityName)

    fun parseError(error: Throwable): String? = errorParser.parse(error)
}