package com.vlatko.data.network

import com.google.gson.GsonBuilder
import com.vlatko.domain.repositories.INetworkConfigRepo

class NetworkConfigImpl : INetworkConfigRepo {

    override fun setBaseUrl(baseUrl: String) {
        RetrofitConfig.init(baseUrl)
        RetrofitConfig.setCustomGson(
            GsonBuilder()
                .setPrettyPrinting()
                .create()
        )
    }
}