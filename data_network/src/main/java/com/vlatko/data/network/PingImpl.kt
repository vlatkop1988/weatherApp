package com.vlatko.data.network

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.InetSocketAddress
import java.net.Socket

class PingImpl : IPing {
    override fun ping() = Single.fromCallable {
        with(Socket()) {
            connect(InetSocketAddress("8.8.8.8", 53), 1500)
            close()
        }
    }.subscribeOn(Schedulers.io())
}