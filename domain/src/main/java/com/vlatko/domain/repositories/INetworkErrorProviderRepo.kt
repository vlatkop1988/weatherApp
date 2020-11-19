package com.vlatko.domain.repositories

import io.reactivex.disposables.Disposable

interface INetworkErrorProviderRepo {

    fun onConnectivityError(consumer: (Unit) -> Unit): Disposable
    fun onAuthorizationError(consumer: (Unit) -> Unit): Disposable
    fun onUnknownError(consumer: (Unit) -> Unit): Disposable
}
