package com.vlatko.weatherapp

import android.content.Context
import com.vlatko.data.network.ApiRepoImpl
import com.vlatko.data.network.NetworkConfigImpl
import com.vlatko.data.network.NetworkConnectivityRepoImpl
import com.vlatko.data.network.NetworkErrorProviderRepoImpl
import com.vlatko.domain.repositories.IApiRepo
import com.vlatko.domain.repositories.INetworkConfigRepo
import com.vlatko.domain.repositories.INetworkConnectivityRepo
import com.vlatko.domain.repositories.INetworkErrorProviderRepo
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

class ModuleGraph {
    fun get(context: Context): Module = module {
        single<IApiRepo> { ApiRepoImpl() }
        single<INetworkErrorProviderRepo> { NetworkErrorProviderRepoImpl() }
        single<INetworkConfigRepo> { NetworkConfigImpl() }
        single<INetworkConnectivityRepo> { NetworkConnectivityRepoImpl() }
    }
}