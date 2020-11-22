package com.vlatko.domain.repositories

interface ILocalStorageRepo {

    fun addFavoriteCity(city: String)
    fun removeFavoriteCity(city: String)
    fun getFavoriteCities(): ArrayList<String>

}