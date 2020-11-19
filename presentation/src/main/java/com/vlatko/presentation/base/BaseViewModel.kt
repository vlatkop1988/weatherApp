package com.vlatko.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.standalone.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val compositeDisposable = CompositeDisposable()
    public override fun onCleared() = run { super.onCleared(); compositeDisposable.clear() }

}