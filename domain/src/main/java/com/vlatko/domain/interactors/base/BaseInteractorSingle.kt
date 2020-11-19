package com.vlatko.domain.interactors.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single

abstract class BaseInteractorObservable<R, E> : BaseInteractor<Observable<com.vlatko.domain.models.base.Result<R, E>>>() {
    override fun execute(observationScheduler: Scheduler): Observable<com.vlatko.domain.models.base.Result<R, E>> =
        internalExecute().observeOn(observationScheduler)
}