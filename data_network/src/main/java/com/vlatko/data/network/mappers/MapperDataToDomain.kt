package com.vlatko.data.network.mappers

import com.vlatko.data.network.response.*
import com.vlatko.domain.models.*

fun ResponseCurrentWeather.toDomain() = CurrentWeather(
    coord = this.coord?.toDomain(),
    weather = this.weather?.map { it.toDomain() },
    base = this.base,
    main = this.main?.toDomain(),
    visibility = this.visibility,
    dt = this.dt,
    wind = this.wind?.toDomain(),
    clouds = this.clouds?.toDomain(),
    sys = this.sys?.toDomain(),
    id = this.id,
    name = this.name,
    cod = this.cod
)

fun ResponseCoordinates.toDomain() = Coordinates(
    lat = this.lat,
    lon = this.lon
)

fun ResponseWeather.toDomain() = Weather(
    id = this.id,
    main = this.main,
    description = this.description,
    icon = this.icon
)

fun ResponseMain.toDomain() = Main(
    temp = this.temp,
    pressure = this.pressure,
    humidity = this.humidity,
    tempMin = this.temp_min,
    tempMax = this.temp_max
)

fun ResponseWind.toDomain() = Wind(
    speed = this.speed,
    deg = this.deg
)

fun ResponseClouds.toDomain() = Clouds(
    all = this.all
)

fun ResponseSys.toDomain() = Sys(
    type = this.type,
    id = this.id,
    message = this.message,
    country = this.country,
    sunrise = this.sunrise,
    sunset = this.sunset
)
