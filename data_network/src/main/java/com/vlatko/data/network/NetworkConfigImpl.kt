package com.vlatko.data.network

import com.google.gson.GsonBuilder
import com.vlatko.data.network.interceptors.X_AUTH_TOKEN
import com.vlatko.data.network.interceptors.X_LOCALE
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

    override fun setAuthToken(token: String?) {
        RetrofitConfig.setHeader(X_AUTH_TOKEN, token)
    }

    override fun setLanguage(language: String?) {
        RetrofitConfig.setHeader(X_LOCALE, language)
    }
}