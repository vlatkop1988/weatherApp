package com.vlatko.presentation.base

import com.vlatko.domain.interactors.GetCurrentWeatherByCityNameInteractor
import com.vlatko.domain.models.CurrentWeather
import io.reactivex.rxkotlin.addTo

class HostActivityViewModel  : BaseViewModel(){
    fun getCurrentWeather(cityName: String, success: ((CurrentWeather) -> Unit)?, error: ((String?) -> Unit)?) {
        GetCurrentWeatherByCityNameInteractor(cityName)
            .execute()
            .subscribe {
                it.process(
                    { success?.invoke(it) },
                    { message -> error?.invoke(message) }
                )
            }.addTo(compositeDisposable)
    }
}