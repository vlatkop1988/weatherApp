package com.vlatko.domain.interactors.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.standalone.KoinComponent

abstract class BaseInteractor<T> : KoinComponent {

    internal abstract fun internalExecute(): T

    abstract fun execute(observationScheduler: Scheduler): T

    fun execute(): T = execute(AndroidSchedulers.mainThread())
}