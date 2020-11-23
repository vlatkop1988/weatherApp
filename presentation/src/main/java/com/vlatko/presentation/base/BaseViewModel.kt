package com.vlatko.presentation.base

import androidx.lifecycle.ViewModel
import com.vlatko.domain.repositories.ILocalStorageRepo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    private val localStorageRepo: ILocalStorageRepo by inject()
    fun storeTheme(theme: Int) = localStorageRepo.storeTheme(theme)
    fun getTheme() = localStorageRepo.getTheme()
    fun storeUnit(unit: String) = localStorageRepo.storeUnit(unit)
    fun getUnit() = localStorageRepo.getUnit()
}