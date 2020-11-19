package com.vlatko.data.network

import com.vlatko.domain.repositories.INetworkErrorProviderRepo
import io.reactivex.disposables.Disposable

class NetworkErrorProviderRepoImpl : INetworkErrorProviderRepo {

    override fun onAuthorizationError(consumer: (Unit) -> Unit): Disposable {
        return RetrofitConfig.subscribeToAuthorizationErrors(consumer)
    }

    override fun onUnknownError(consumer: (Unit) -> Unit): Disposable {
        return RetrofitConfig.subscribeToUnknownErrors(consumer)
    }

    override fun onConnectivityError(consumer: (Unit) -> Unit): Disposable {
        return RetrofitConfig.subscribeToConnectivityErrors(consumer)
    }
}