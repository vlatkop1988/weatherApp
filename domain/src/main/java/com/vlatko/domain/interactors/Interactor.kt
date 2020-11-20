package com.vlatko.domain.interactors

import io.reactivex.Observable
import org.koin.standalone.KoinComponent

interface Interactor<R> : KoinComponent {
    fun execute(): Observable<R>
}