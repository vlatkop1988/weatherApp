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
        ) ?: FavoriteCities()
    }

    private fun storeFavoritePlaces(favoritePlaces: FavoriteCities) {
        sharedPreferences.edit()
            .putString(
                KEY_FAVORITE_CITIES,
                Gson().toJson(favoritePlaces, FavoriteCities::class.java)
            ).apply()
    }

    override fun storeUnit(unit: String) {
        sharedPreferences.edit().putString(KEY_UNIT, unit).apply()
    }

    override fun getUnit(): String = sharedPreferences.getString(KEY_UNIT, "metric") ?: "metric"

    override fun storeTheme(theme: Int) {
        sharedPreferences.edit().putInt(KEY_THEME, theme).apply()
    }

    override fun getTheme(): Int = sharedPreferences.getInt(KEY_THEME, -1)

    companion object {
        private const val KEY_FAVORITE_CITIES = "key_favorite_cities"
        private const val KEY_UNIT = "key_unit"
        private const val KEY_THEME = "key_theme"
    }
}