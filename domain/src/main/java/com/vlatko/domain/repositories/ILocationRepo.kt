package com.vlatko.domain.repositories

import android.location.Location
import io.reactivex.Observable

interface ILocationRepo {
    fun getCurrentLocation(): Observable<Location>
}