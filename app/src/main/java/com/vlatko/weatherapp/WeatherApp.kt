package com.vlatko.weatherapp

import android.app.Application
import com.vlatko.domain.repositories.INetworkConfigRepo
import org.koin.android.ext.android.startKoin
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.*

class WeatherApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(ModuleGraph().get(this)))
        val networkConfig: INetworkConfigRepo by inject()

        networkConfig.setBaseUrl(BuildConfig.BASE_URL)
    }
}