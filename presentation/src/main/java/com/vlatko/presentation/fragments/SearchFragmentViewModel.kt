package com.vlatko.presentation.fragments

import com.vlatko.domain.IErrorParser
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.repositories.IApiRepo
import com.vlatko.domain.repositories.ILocalStorageRepo
import com.vlatko.presentation.base.BaseViewModel
import io.reactivex.Observable
import org.koin.standalone.inject
import java.util.*

class SearchFragmentViewModel : BaseViewModel() {
    private val apiRepo: IApiRepo by inject()
    private val localStorageRepo: ILocalStorageRepo by inject()
    private val errorParser: IErrorParser by inject()
    private val lang = Locale.getDefault().language

    fun searchForCity(cityName: String?): Observable<CurrentWeather> =
        apiRepo.getCurrentWeatherByCityName(cityName, localStorageRepo.getUnit(), lang)

    fun addFavoriteCity(cityName: String) =
        localStorageRepo.addFavoriteCity(cityName)

    fun parseError(error: Throwable): String? =
        errorParser.parse(error)
}