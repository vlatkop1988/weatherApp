package com.vlatko.domain.repositories

import io.reactivex.disposables.Disposable

interface INetworkConnectivityRepo {
    fun isNetworkConnected(success: () -> Unit, error: () -> Unit): Disposable
}
