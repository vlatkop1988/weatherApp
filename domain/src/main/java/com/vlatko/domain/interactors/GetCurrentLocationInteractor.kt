package com.vlatko.domain.interactors

import android.location.Location
import com.vlatko.domain.repositories.ILocationRepo
import io.reactivex.Observable
import org.koin.standalone.inject

class GetCurrentLocationInteractor : Interactor<Location> {
    private val locationRepo: ILocationRepo by inject()
    override fun execute(): Observable<Location> = locationRepo.getCurrentLocation()
}