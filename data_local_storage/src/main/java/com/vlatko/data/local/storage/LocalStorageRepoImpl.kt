package com.vlatko.data.local.storage

import android.content.Context
import com.google.gson.Gson
import com.vlatko.domain.models.FavoriteCities
import com.vlatko.domain.repositories.ILocalStorageRepo

class LocalStorageRepoImpl(context: Context) : ILocalStorageRepo {

    private val sharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    override fun addFavoriteCity(city: String) =
        storeFavoritePlaces(getFavoritePlaces().apply { addFavoriteCity(city) })

    override fun removeFavoriteCity(city: String) =
        storeFavoritePlaces(getFavoritePlaces().apply { removeFavoriteCity(city) })

    override fun getFavoriteCities() = getFavoritePlaces().getFavoriteCities()

    private fun getFavoritePlaces(): FavoriteCities {
        return Gson().fromJson(
            sharedPreferences.getString(KEY_FAVORITE_CITIES, ""),
            FavoriteCities::class.java
        )
            ?: FavoriteCities()
    }

    private fun storeFavoritePlaces(favoritePlaces: FavoriteCities) {
        sharedPreferences.edit()
            .putString(
                KEY_FAVORITE_CITIES,
                Gson().toJson(favoritePlaces, FavoriteCities::class.java)
            ).apply()
    }

    companion object {
        private const val KEY_FAVORITE_CITIES = "key_favorite_cities"
    }
}