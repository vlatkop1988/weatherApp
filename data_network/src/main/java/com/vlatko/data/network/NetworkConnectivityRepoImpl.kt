package com.vlatko.data.network

import com.vlatko.domain.repositories.INetworkConnectivityRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class NetworkConnectivityRepoImpl : INetworkConnectivityRepo {
    override fun isNetworkConnected(success: () -> Unit, error: () -> Unit): Disposable =
        PingImpl().ping()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ success() }, { error() })
}