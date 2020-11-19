package com.vlatko.domain.repositories

interface INetworkConfigRepo {

    fun setBaseUrl(baseUrl: String)
    fun setAuthToken(token: String?)
    fun setLanguage(language : String?)
}