package com.vlatko.domain.models

class FavoriteCities {

    private var favoriteCities: ArrayList<String> = ArrayList()

    fun addFavoriteCity(city: String) = add(city, favoriteCities)
    fun removeFavoriteCity(city: String) = remove(city, favoriteCities)
    fun getFavoriteCities(): ArrayList<String> = favoriteCities

    private fun add(item: String, items: ArrayList<String>) = items.let {
        if (!it.contains(item)) it.add(item)
        else return@let
    }

    private fun remove(item: String, items: ArrayList<String>) = items.let {
        if (it.contains(item)) it.remove(item)
        else return@let
    }
}