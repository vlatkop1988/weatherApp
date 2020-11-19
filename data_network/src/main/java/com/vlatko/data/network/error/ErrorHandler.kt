package com.vlatko.data.network.error

import android.annotation.SuppressLint
import android.util.Log
import com.vlatko.data.network.IPing
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

private const val TAG = "ErrorHandler"

internal class ErrorHandler {

    lateinit var iPing: IPing
    private val connectivityPublisher = PublishSubject.create<Unit>()
    private val authorizationPublisher = PublishSubject.create<Unit>()
    private val unknownPublisher = PublishSubject.create<Unit>()

    internal fun subscribeToConnectivityErrors(consumer: (Unit) -> Unit): Disposable =
        connectivityPublisher.observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)

    internal fun subscribeToAuthorizationErrors(consumer: (Unit) -> Unit): Disposable =
        authorizationPublisher.observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)

    internal fun subscribeToUnknownErrors(consumer: (Unit) -> Unit): Disposable =
        unknownPublisher.observeOn(AndroidSchedulers.mainThread()).subscribe(consumer)

    internal fun handleError(error: Throwable, apiErrorListener: (APIError) -> Unit) {
        when (error) {
            is UnknownHostException -> {
                Log.d(TAG, "UnknownHostError $error")
                unknownPublisher.onNext(Unit)
            }
            is HttpException -> handleHttpError(error, apiErrorListener)
            is IOException -> {
                Log.d(TAG, "IOError $error")
                handleNetworkError(apiErrorListener)
            }
            else -> {
                Log.d(TAG, "UnknownError $error")
                unknownPublisher.onNext(Unit)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun handleNetworkError(apiErrorListener: (APIError) -> Unit) {
        iPing.ping().subscribe(
            {
                Log.d(TAG, "Network error UNKNOWN")
                apiErrorListener(APIError(reason = Reason.UNKNOWN))
                unknownPublisher.onNext(Unit)
            },
            {
                Log.d(TAG, "Network error NETWORK_DOWN")
                apiErrorListener(APIError(reason = Reason.NETWORK_DOWN))
                connectivityPublisher.onNext(Unit)
            }
        )
    }

    private fun handleHttpError(error: HttpException, apiErrorListener: (APIError) -> Unit) {
        when (error.code()) {
            400, 404, 406, 422 -> {
                Log.d(TAG, "${error.code()}")
                apiErrorListener(APIError(error, Reason.API))
            }
            401 -> {
                Log.d(TAG, "401")
                authorizationPublisher.onNext(Unit)
            }
            403 -> {
                apiErrorListener(APIError(error, Reason.API))
                Log.d(TAG, "403")
            }
            500, 503 -> {
                Log.d(TAG, "${error.code()}")
                apiErrorListener(APIError(error, Reason.SERVER))
                unknownPublisher.onNext(Unit)
            }
            else -> {
                Log.d(TAG, "other http")
                apiErrorListener(APIError(error, Reason.API))
            }
        }
    }
}