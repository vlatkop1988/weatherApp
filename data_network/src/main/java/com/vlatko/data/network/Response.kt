package com.vlatko.data.network

import com.vlatko.data.network.error.APIError
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.observers.DisposableObserver

class Response<T, E>(
    private val source: Observable<T>,
    private val success: (T) -> E,
    private val error: (APIError) -> E
) : DisposableObserver<T>() {

    private lateinit var emitter: Emitter<E>
    override fun onComplete() {}

    override fun onNext(t:T) = emitter.onNext(success(t))

    override fun onError(e: Throwable) = RetrofitConfig.errorHandler.handleError(e) { onError(it) }

    private fun onError(apiError: APIError) = emitter.onNext(error(apiError))

    fun toObservable(): Observable<E> {
        return Observable.create {
            emitter = it
            source.subscribeWith(this)
        }
    }
}