package com.vlatko.domain.models

data class CurrentWeather(
    val coord: Coordinates? = null,
    val weather: List<Weather>? = null,
    val base: String? = null,
    val main: Main? = null,
    val visibility: Int? = null,
    val wind: Wind? = null,
    val clouds: Clouds? = null,
    val dt: Long? = null,
    val sys: Sys? = null,
    val id: Long? = null,
    val name: String? = null,
    val cod: Int? = null
)

data class Coordinates(
    val lon: String? = null,
    val lat: String? = null
)

data class Weather(
    val id: Long? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

data class Main(
    val temp: Double? = null,
    val pressure: Double? = null,
    val humidity: Double? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
    val feelsLike: Double? = null
)

data class Wind(
    val speed: Double? = null,
    val deg: Int? = null
)

data class Clouds(
    val all: Int? = null
)

data class Sys(
    val type: Int? = null,
    val id: Long? = null,
    val message: Double? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)