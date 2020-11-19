package com.vlatko.data.network.response

data class ResponseCurrentWeather(
    val coord: ResponseCoordinates? = null,
    val weather: List<ResponseWeather>? = null,
    val base: String? = null,
    val main: ResponseMain? = null,
    val visibility: Int? = null,
    val wind: ResponseWind? = null,
    val clouds: ResponseClouds? = null,
    val dt: Long? = null,
    val sys: ResponseSys? = null,
    val id: Long? = null,
    val name: String? = null,
    val cod: Int? = null
)

data class ResponseCoordinates(
    val lon: String? = null,
    val lat: String? = null
)

data class ResponseWeather(
    val id: Long? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

data class ResponseMain(
    val temp: Double? = null,
    val pressure: Double? = null,
    val humidity: Double? = null,
    val temp_min: Double? = null,
    val temp_max: Double? = null
)

data class ResponseWind(
    val speed: Double? = null,
    val deg: Int? = null
)

data class ResponseClouds(
    val all: Int? = null
)

data class ResponseSys(
    val type: Int? = null,
    val id: Long? = null,
    val message: Double? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)