package com.vlatko.presentation.fragments

import com.vlatko.domain.repositories.ILocalStorageRepo
import com.vlatko.presentation.base.BaseViewModel
import org.koin.standalone.inject

class FavoritesFragmentViewModel : BaseViewModel() {

    private val localStorageRepo: ILocalStorageRepo by inject()

    fun getFavoriteCities(): ArrayList<String> = localStorageRepo.getFavoriteCities()
    fun removeFavoriteCity(cityName: String) = localStorageRepo.removeFavoriteCity(cityName)

}