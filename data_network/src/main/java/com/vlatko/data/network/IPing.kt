package com.vlatko.data.network

import io.reactivex.Single

interface IPing {
    fun ping(): Single<Unit?>
}