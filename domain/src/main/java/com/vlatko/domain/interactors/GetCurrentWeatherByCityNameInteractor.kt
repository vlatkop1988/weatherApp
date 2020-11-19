package com.vlatko.domain.interactors

import com.vlatko.domain.interactors.base.BaseInteractorObservable
import com.vlatko.domain.models.CurrentWeather
import com.vlatko.domain.models.base.Result
import com.vlatko.domain.repositories.IApiRepo
import io.reactivex.Observable
import org.koin.standalone.inject

class GetCurrentWeatherByCityNameInteractor(private val cityName: String) :
    BaseInteractorObservable<CurrentWeather, String>() {
    private val iApiRepo: IApiRepo by inject()
    override fun internalExecute(): Observable<Result<CurrentWeather, String>> =
        iApiRepo.getCurrentWeatherByCityName(cityName)
}