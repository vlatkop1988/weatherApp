package com.vlatko.weatherapp

import android.content.Context
import com.vlatko.data.local.storage.LocalStorageRepoImpl
import com.vlatko.data.location.LocationRepoImpl
import com.vlatko.data.network.*
import com.vlatko.domain.IErrorParser
import com.vlatko.domain.repositories.IApiRepo
import com.vlatko.domain.repositories.ILocalStorageRepo
import com.vlatko.domain.repositories.ILocationRepo
import com.vlatko.domain.repositories.INetworkConfigRepo
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

class ModuleGraph {
    fun get(context: Context): Module = module {
        single<IApiRepo> { ApiRepoImpl() }
        single<ILocationRepo> { LocationRepoImpl(context) }
        single<INetworkConfigRepo> { NetworkConfigImpl() }
        single<ILocalStorageRepo> { LocalStorageRepoImpl(context) }
        single<IErrorParser> { ApiErrorParserImpl() }
    }
}