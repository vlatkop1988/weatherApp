package com.vlatko.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import com.vlatko.domain.repositories.ILocationRepo
import io.reactivex.Observable

class LocationRepoImpl(context: Context) : ILocationRepo {
    private val rxLocation =  RxLocation(context)
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Observable<Location> {
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setSmallestDisplacement(2F)
            .setInterval(2000)
        return rxLocation.location().updates(locationRequest)
    }
}