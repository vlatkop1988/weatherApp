package com.vlatko.data.network

class ServiceFactory private constructor() {

    private val apiServices = HashMap<Class<*>, Any>()

    @Synchronized
    fun createAndGetService(serviceClass: Class<*>): Any? {
        if (!apiServices.containsKey(serviceClass)) {
            apiServices[serviceClass] = RetrofitConfig.get().create(serviceClass) as Any
        }
        return apiServices[serviceClass]
    }

    inline fun <reified T> getService(serviceClass: Class<T>): T {
        return createAndGetService(serviceClass) as T
    }

    companion object {
        val instance = ServiceFactory()
    }
}